#ifdef __APPLE__
#include <OpenCL/opencl.h>
#else
#include <CL/cl.h>
#endif

#define MAX_SOURCE_SIZE (0x100000)
#define MAX_MEM_BUFFERS (0x10)

class WrapOpenCL {

	int memSize;
	char* kernelFile;
	const char* kernelName;
	
	char *source_str;
        size_t source_size;

	cl_device_id device_id;
        cl_context context;
        cl_command_queue command_queue;
        cl_program program;
        cl_platform_id platform_id;
        cl_uint ret_num_devices;
        cl_uint ret_num_platforms;

	//cl_mem memobj[];

	cl_event ev;

public:
        cl_kernel kernel;

	WrapOpenCL(const char* kernelName);
	WrapOpenCL(int memSize, const char* kernelName);

	void initCL();
	void invoke();
	void invoke(int global_size, int local_size);
	int 		getMemSize();
	void freeResources();

	static char *	parseArgument(int argc, char* argv[]); 

};
