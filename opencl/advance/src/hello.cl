__kernel void hello(__global char* string)
{
string[0] = 'H';
string[1] = 'e';
string[2] = 'l';
string[3] = 'l';
string[4] = '0';
string[5] = ',';
string[6] = ' ';
string[7] = 'M';
string[8] = 'a';
string[9] = 'n';
string[10] = 'u';
string[11] = '!';
string[12] = '\0';
}
