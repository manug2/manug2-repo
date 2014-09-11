#ifdef __APPLE__
#include <OpenCL/opencl.h>
#else
#include <CL/cl.h>
#endif

#define MAX_SOURCE_SIZE (0x100000)
#define MAX_MEM_BUFFERS (0x10)

class WrapOpenCL {

private:
	char *readSourceCL(size_t &source_size);
	void	initNULL();
	char*	kernelFile;
	const char*	kernelName;


	int memSize;
	
	cl_device_id	device_id;
        cl_program	program;
        cl_platform_id	platform_id;
        cl_uint	ret_num_devices;
        cl_uint	ret_num_platforms;

        cl_kernel kernel;
        cl_context context;
        cl_command_queue command_queue;
	cl_event ev;

public:
	void print();

	~WrapOpenCL();
	WrapOpenCL(char* kernelName);
	WrapOpenCL(int memSize, char* kernelName);

	void	initCL();
	void	invoke();
	void	invoke(int global_size, int local_size);
	int 	getMemSize();
	void	setKernelArg(int argn, size_t arg_size, void * arg_ptr);
	cl_mem	createBuffer(cl_mem_flags flags, size_t buf_size, void * host_ptr);
	void	readBuffer(cl_mem memobj, cl_bool blocking_write, size_t offset, size_t buf_size, void *ptr, cl_uint num_of_events_in_wait_list, cl_event *event_wait_list);

	static char *	parseArgument(int argc, char* argv[]); 
	

};
