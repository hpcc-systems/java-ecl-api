import std;

std.file.sprayVariable('192.168.59.129','/var/lib/HPCCSystems/mydropzone/person.csv',
                         8192,',','\n,\r\n',,'mythor','in::person2',-1,
                         'http://192.168.59.129:8010/FileSpray',,,true);