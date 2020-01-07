cmake_minimum_required(VERSION 3.4.1)

include_directories(breakpadlib/src breakpadlib/src/common/android/include)

add_subdirectory(breakpadlib)

add_library(breakpad-core SHARED breakpad.cpp)

target_link_libraries(breakpad-core breakpad-lib log)