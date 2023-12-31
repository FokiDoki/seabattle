# Sea Battle CLI
    
Sea Battle is a thrilling and strategic command-line interface (CLI) game that brings the classic naval combat experience to your terminal. Engage in intense battles on the high seas as you command your fleet of battleships and submarines, aiming to outmaneuver and outgun your opponent.

## How to run
1. [Install Java 20](#how-to-install-java-20)
2. Download latest jar from [releases](https://github.com/FokiDoki/seabattle/releases)
3. For linux only:
   ```
   sudo apt update
   sudo apt install libxkbcommon-x11-0
   ```
5. Run application with `java -jar seabattle-1.0.jar` (replace with your version)

### Screenshots
![image](https://github.com/FokiDoki/seabattle/assets/23121394/a7ac6d02-fcb7-4397-8d31-1e8914b5166b)
![image](https://github.com/FokiDoki/seabattle/assets/23121394/617f3fc6-bf86-4b96-88df-fcdeddba3314)
![image](https://github.com/FokiDoki/seabattle/assets/23121394/613e75d2-fe8d-41c4-9fa8-ab19aaeb2569)



### How to install Java 20

---

#### For x64 systems:
```shell
sudo apt-get update
wget https://download.oracle.com/java/20/latest/jdk-20_linux-x64_bin.deb
sudo dpkg -i jdk-20_linux-x64_bin.deb
```
\
If you get an error `dpkg: error processing package jdk-20` \
Run this command:
```shell
apt --fix-broken install
```
And then run dpkg again ```sudo dpkg -i jdk-20_linux-x64_bin.deb```

#### For arm64 systems:
```shell
wget https://download.oracle.com/java/20/latest/jdk-20_linux-aarch64_bin.rpm
sudo rpm -i jdk-20_linux-aarch64_bin.rpm
```

#### Add JAVA_HOME to your environment variables (for all systems):
Open `.bashrc` file
```shell
nano ~/.bashrc
```

Add this lines to the end of the file:
```shell
export JAVA_HOME="/usr/lib/jvm/jdk-20/"
export PATH=$JAVA_HOME/bin:$PATH
```
Save and exit the file, then **relogin**

Then check if java is installed:
```shell
java -version
> java version "20.0.1" 2023-04-18
> Java(TM) SE Runtime Environment (build 20.0.1+9-29)
> Java HotSpot(TM) 64-Bit Server VM (build 20.0.1+9-29, mixed mode, sharing)
```

[Continue installation](#how-to-run)


### Known bugs
1. [Windows] App not shutting down if you close windows
2. [Windows] Key listener listening always
