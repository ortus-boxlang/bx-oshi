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
* `getJVMMaxMemory()` : Gets the max memory of the JVM

Please note that with access to the hardware and operating system, you can get a lot more information.  Please visit the OSHI documentation for more information: https://www.oshi.ooo/oshi-core-java11/apidocs/com.github.oshi/oshi/package-summary.html

## Ortus Sponsors

BoxLang is a professional open-source project and it is completely funded by the [community](https://patreon.com/ortussolutions) and [Ortus Solutions, Corp](https://www.ortussolutions.com).  Ortus Patreons get many benefits like a cfcasts account, a FORGEBOX Pro account and so much more.  If you are interested in becoming a sponsor, please visit our patronage page: [https://patreon.com/ortussolutions](https://patreon.com/ortussolutions)

### THE DAILY BREAD

 > "I am the way, and the truth, and the life; no one comes to the Father, but by me (JESUS)" Jn 14:1-12
