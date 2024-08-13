# ⚡︎ BoxLang OS & Hardware Info

```
|:------------------------------------------------------:|
| ⚡︎ B o x L a n g ⚡︎
| Dynamic : Modular : Productive
|:------------------------------------------------------:|
```

<blockquote>
	Copyright Since 2023 by Ortus Solutions, Corp
	<br>
	<a href="https://www.boxlang.io">www.boxlang.io</a> |
	<a href="https://www.ortussolutions.com">www.ortussolutions.com</a>
</blockquote>

<p>&nbsp;</p>

## Welcome to BoxLang OSHI

This module is based on the great work of the `oshi` library https://github.com/oshi/oshi?tab=readme-ov-file#documentation.  You can use this module to get information about the Operating System and Hardware of the machine you are running on.  This is a great way to even get sensor information or embedded system information like battery, raspberry pi, etc.

> OSHI is a free JNA-based (native) Operating System and Hardware Information library for Java. It does not require the installation of any additional native libraries and aims to provide a cross-platform implementation to retrieve system information, such as OS version, processes, memory and CPU usage, disks and partitions, devices, sensors, etc.

## Supported Features

* Read/Write system information from ini files
* Computer System and firmware, baseboard
* Operating System and Version/Build
* Physical (core) and Logical (hyperthreaded) CPUs, processor groups, NUMA nodes
* System and per-processor load, usage tick counters, interrupts, uptime
* Process uptime, CPU, memory usage, user/group, command line args, thread details
* Physical and virtual memory used/available
* Mounted filesystems (type, usable and total space, options, reads and writes)
* Disk drives (model, serial, size, reads and writes) and partitions
* Network interfaces (IPs, bandwidth in/out), network parameters, TCP/UDP statistics
* Battery state (% capacity, time remaining, power usage stats)
* USB Devices
* Connected displays (with EDID info), graphics and audio cards
* Sensors (temperature, fan speeds, voltage) on some hardware

## Contributed Functions

Here are the contributed functions in this module:

* `getSystemInfo()` : Get's the main entry point for the OSHI system: https://www.oshi.ooo/oshi-core-java11/apidocs/com.github.oshi/oshi/SystemInfo.html
* `getOperatingSystem()` : Get's the Operating System information: https://www.oshi.ooo/oshi-core-java11/apidocs/com.github.oshi/oshi/software/os/OperatingSystem.html
* `getHardware()` : Get's the Hardware information: https://www.oshi.ooo/oshi-core-java11/apidocs/com.github.oshi/oshi/hardware/HardwareAbstractionLayer.html

The following are also contributed functions provided by convenience:

* `getCpuUsage( [interval] )` : Gets the CPU usage of the system with a custom interval
* `getFreeSpace( path )` : Gets the free space of a drive
* `getTotalSpace( path )` : Gets the total space of a drive
* `getSystemFreeMemory()` : Gets the free memory of the operating system
* `getSystemTotalMemory()` : Gets the total memory of the operating system
* `getJVMFreeMemory()` : Gets the free memory of the JVM
* `getJVMTotalMemory()` : Gets the total memory of the JVM

### Ini Files Support

This module also provides a way to read and write initialization files.

```ini
[General]
appName=MyApplication
version=1.2.3
author=John Doe
boxlang=rocks

[Database]
host=localhost
port=5432
username=dbuser
password=dbpass
dbname=mydatabase

[Logging]
logLevel=DEBUG
logFile=/var/log/myapp.log
maxFileSize=10MB

[Features]
enableFeatureX=true
enableFeatureY=false
maxConnections=100
```

You can use the following functions to read and write ini files:

* `getInitFile( file )` : Reads an ini file and returns the IniFile object. If the file does not exist, it will create it.
* `getProfileSection( iniFile, section )` : Gets a section from the ini file as a struct
* `getProfileSections( iniFile )` : Gets all the sections from the ini file as a struct of structs
* `getProfileString( iniFile, section, entry )` : Gets an entry from a section in the ini file, if it does not exist, it will return an empty string
* `setProfileString( iniFile, section, entry, value )` : Sets an entry in a section in the ini file, if the section does not exist, it will create it
* `removeProfileSection( iniFile, section )` : Removes a section from the ini file
* `removeProfileString( iniFile, section, entry )` : Removes an entry from a section in the ini file

The `IniFile` object is a fluent object that allows you to work with ini files in a very easy way.  Here is an example of how to use it:

```java

// Get the ini file
var iniFile = getInitFile( "test.ini" );
iniFile.createSection( "mySettings" );
// Set a string
iniFile.setEntry( "section1", "entry1", "value1" );
// Get a string
var value = iniFile.getEntry( "section1", "entry1" );
// Remove a string
iniFile.removeEntry( "section1", "entry1" );
// Remove a section
iniFile.removeSection( "section1" );
```

Please note that with access to the hardware and operating system, you can get a lot more information.  Please visit the OSHI documentation for more information: https://www.oshi.ooo/oshi-core-java11/apidocs/com.github.oshi/oshi/package-summary.html

## Ortus Sponsors

BoxLang is a professional open-source project and it is completely funded by the [community](https://patreon.com/ortussolutions) and [Ortus Solutions, Corp](https://www.ortussolutions.com).  Ortus Patreons get many benefits like a cfcasts account, a FORGEBOX Pro account and so much more.  If you are interested in becoming a sponsor, please visit our patronage page: [https://patreon.com/ortussolutions](https://patreon.com/ortussolutions)

### THE DAILY BREAD

 > "I am the way, and the truth, and the life; no one comes to the Father, but by me (JESUS)" Jn 14:1-12
