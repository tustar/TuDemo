#ifndef TUDEMO_MEMORYFILE_H
#define TUDEMO_MEMORYFILE_H

#include <string>


class MemoryFile {
public:
    MemoryFile(const char *path);

    ~MemoryFile();

public:
    void write(char *data, int datalen);

private:
    void resize(int32_t needSize);

private:
    //文件路径
    std::string m_path;
    //打开的文件句柄
    int m_fd;
    //文件可写长度
    int32_t m_size;
    //文件内容数据
    int8_t *m_ptr;
    //已经使用的长度
    int32_t m_actualSize;
};


#endif //TUDEMO_MEMORYFILE_H
