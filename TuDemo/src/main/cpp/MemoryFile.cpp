#include "MemoryFile.h"
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <unistd.h>

//系统给我们提供真正的内存时，用页为单位提供
//内存分页大小 一分页的大小
int32_t DEFAULT_FILE_SIZE = getpagesize();


MemoryFile::MemoryFile(const char *path) {
    m_path = path;
    m_fd = open(m_path.c_str(), O_RDWR | O_CREAT, S_IRWXU);
    m_size = DEFAULT_FILE_SIZE;
    ftruncate(m_fd, m_size);
    //mmap内存映射
    m_ptr = static_cast<int8_t *>(mmap(0, m_size, PROT_READ | PROT_WRITE, MAP_SHARED,
                                       m_fd, 0));
    m_actualSize = 0;
}

MemoryFile::~MemoryFile() {
    munmap(m_ptr, m_size);
    close(m_fd);
}

void MemoryFile::write(char *data, int datalen) {
    if (m_actualSize + datalen >= m_size) {
        resize(m_actualSize + datalen);
    }
    memcpy(m_ptr + m_actualSize, data, datalen);
    m_actualSize += datalen;
}

void MemoryFile::resize(int32_t needSize) {
    int32_t oldSize = m_size;
    do {
        m_size *= 2;
    } while (m_size < needSize);

    ftruncate(m_fd, m_size);
    //重新映射
    munmap(m_ptr, oldSize);
    m_ptr = (int8_t *) mmap(m_ptr, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd, 0);
}
