# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#set(BREAKPAD_ROOT ${CMAKE_CURRENT_SOURCE_DIR})

include_directories(breakpadlib/src breakpadlib/src/common/android/include)

#file(GLOB variable [RELATIVE path] [globbing expressions]...)
#GLOB选项将会为所有匹配查询表达式的文件生成一个文件list，并将该list存储进变量variable里
#file(GLOB BREAKPAD_CC_SOURCES *.cc)
#https://cmake.org/cmake/help/latest/command/file.html
file(GLOB BREAKPAD_CC_SOURCES
        src/client/linux/crash_generation/crash_generation_client.cc
        src/client/linux/dump_writer_common/thread_info.cc
        src/client/linux/dump_writer_common/ucontext_reader.cc
        src/client/linux/handler/exception_handler.cc
        src/client/linux/handler/minidump_descriptor.cc
        src/client/linux/log/log.cc
        src/client/linux/microdump_writer/microdump_writer.cc
        src/client/linux/minidump_writer/linux_dumper.cc
        src/client/linux/minidump_writer/linux_ptrace_dumper.cc
        src/client/linux/minidump_writer/minidump_writer.cc
        src/client/minidump_file_writer.cc
        src/common/convert_UTF.c
        src/common/md5.cc
        src/common/string_conversion.cc
        src/common/linux/elfutils.cc
        src/common/linux/file_id.cc
        src/common/linux/guid_creator.cc
        src/common/linux/linux_libc_support.cc
        src/common/linux/memory_mapped_file.cc
        src/common/linux/safe_readlink.cc)

#file(GLOB BREAKPAD_ASM_SOURCES *.S)
file(GLOB BREAKPAD_ASM_SOURCES src/common/android/breakpad_getcontext.S)

set_source_files_properties(${BREAKPAD_ASM_SOURCES} PROPERTIES LANGUAGE C)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        breakpad-lib

        STATIC

        ${BREAKPAD_CC_SOURCES}
        ${BREAKPAD_ASM_SOURCES})


target_link_libraries(breakpad-lib log)