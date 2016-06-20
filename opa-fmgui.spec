#
# Spec file for Package opa-fmgui
#

# Copyright (c) 2015, Intel Corporation
# All rights reserved.
# 
# Redistribution and use in source and binary forms, with or without modification,
# are permitted provided that the following conditions are met:
# - Redistributions of source code must retain the above copyright notice,
#   this list of conditions and the following disclaimer.
# - Redistributions in binary form must reproduce the above copyright notice,
#   this list of conditions and the following disclaimer in the documentation
#   and/or other materials provided with the distribution.
# - Neither the name of Intel Corporation nor the names of its contributors may
#   be used to endorse or promote products derived from this software without
#   specific prior written permission.
# 
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
# ARE DISCLAIMED. IN NO EVENT SHALL INTEL, THE COPYRIGHT OWNER OR CONTRIBUTORS
# BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
# INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
# CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
# ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGE.
# 
# EXPORT LAWS: THIS LICENSE ADDS NO RESTRICTIONS TO THE EXPORT LAWS OF YOUR
# JURISDICTION. It is licensee's responsibility to comply with any export
# regulations applicable in licensee's jurisdiction. Under CURRENT (May 2000)
# U.S. export regulations this software is eligible for export from the U.S.
# and can be downloaded by or otherwise exported or reexported worldwide EXCEPT
# to U.S. embargoed destinations which include Cuba, Iraq, Libya, North Korea,
# Iran, Syria, Sudan, Afghanistan and any other country to which the U.S. has
# embargoed goods and services.

%global appfolder opa-fmgui
%global appjar opa-fmgui.jar

Name:           opa-fmgui
Version:        10.0.0.0.3
Release:        3%{?dist}
Summary:        Intel Omni-Path Architecture Fabric Manager Graphical User Interface
Group:          Applications/System
# For a breakdown of the licensing, see THIRD-PARTY-README
License:        GPLv2 and LGPLv2+ and MIT and BSD 
URL:            https://github.com/01org/opa-fmgui/wiki/
Source0:        opa-fmgui-10.0.0.0.3.tar.gz
BuildArch:      noarch

BuildRequires: gradle-local
BuildRequires: maven-local
BuildRequires: javapackages-local
BuildRequires: javapackages-tools
BuildRequires: ant
BuildRequires: mvn(log4j:log4j)
BuildRequires: mvn(org.hibernate.common:hibernate-commons-annotations)
BuildRequires: mvn(org.hibernate:hibernate-core)
BuildRequires: mvn(org.hibernate:hibernate-entitymanager)
BuildRequires: mvn(org.hibernate.javax.persistence:hibernate-jpa-2.1-api)
BuildRequires: mvn(org.hsqldb:hsqldb)
BuildRequires: mvn(org.jboss:jandex)
BuildRequires: mvn(com.sun.mail:javax.mail)
BuildRequires: mvn(org.slf4j:log4j-over-slf4j)
BuildRequires: mvn(org.slf4j:slf4j-api)
BuildRequires: mvn(ch.qos.logback:logback-classic)
BuildRequires: mvn(ch.qos.logback:logback-core)
BuildRequires: mvn(org.jfree:jfreechart)
BuildRequires: mvn(org.swinglabs.swingx:swingx-all)
BuildRequires: mvn(org.jfree:jcommon)
BuildRequires: mvn(javax.help:javahelp)
BuildRequires: mvn(org.jboss.logging:jboss-logging)
BuildRequires: mvn(org.jboss.logging:jboss-logging-annotations)
BuildRequires: mvn(org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec)
BuildRequires: mvn(org.javassist:javassist)
BuildRequires: mvn(dom4j:dom4j)
BuildRequires: mvn(antlr:antlr)
BuildRequires: mvn(com.jcraft:jsch)
BuildRequires: mvn(net.engio:mbassador)
BuildRequires: mvn(com.mxgraph:jgraphx)
BuildRequires: desktop-file-utils

Requires: jre >= 1.7
Requires: mvn(log4j:log4j)
Requires: mvn(org.hibernate.common:hibernate-commons-annotations)
Requires: mvn(org.hibernate:hibernate-core)
Requires: mvn(org.hibernate:hibernate-entitymanager)
Requires: mvn(org.hibernate.javax.persistence:hibernate-jpa-2.1-api)
Requires: mvn(org.hsqldb:hsqldb)
Requires: mvn(org.jboss:jandex)
Requires: mvn(com.sun.mail:javax.mail)
Requires: mvn(org.slf4j:log4j-over-slf4j)
Requires: mvn(org.slf4j:slf4j-api)
Requires: mvn(ch.qos.logback:logback-classic)
Requires: mvn(ch.qos.logback:logback-core)
Requires: mvn(org.jfree:jfreechart)
Requires: mvn(org.jfree:jcommon)
Requires: mvn(javax.help:javahelp)
Requires: mvn(org.jboss.logging:jboss-logging)
Requires: mvn(org.jboss.logging:jboss-logging-annotations)
Requires: mvn(org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec)
Requires: mvn(org.javassist:javassist)
Requires: mvn(dom4j:dom4j)
Requires: mvn(antlr:antlr)
Requires: mvn(com.jcraft:jsch)
Requires: mvn(net.engio:mbassador)
Requires: mvn(com.mxgraph:jgraphx)
Requires: mvn(org.swinglabs.swingx:swingx-all)

# Filter all jar files, except "gritty.jar".
%global __provides_exclude_from ^%{_javadir}/%{appfolder}/lib/[^(gritty)].*.jar$

%description
FMGUI is the Intel Omni-Path Architecture Fabric Manager Graphical User 
Interface. It can be run by invoking the Bourne shell script opa-fmgui.

%prep
%autosetup -n %{name}-%{version}
%setup  -q

%build
%if 0%{?fedora} >= 24
    %gradle_build -i copyDeps
%else
    gradle --offline copyDeps build
%endif

%install

%mvn_install
install -m 755 -pDt %{buildroot}/%{_javadir}/%{appfolder} %{appjar}
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder} README
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder} THIRD-PARTY-README
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder} Third_Party_Copyright_Notices_and_Licenses
# All jar files provided by other RPMs had been prevented from scanning for deps.
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder} LICENSE
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder}/gritty gritty/build/libs/gritty.jar
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder}/lib lib/*
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder}/gritty gritty/gritty_license.txt
install -m 755 -pDt %{buildroot}/%{_javadir}/%{appfolder}/bin bin/buildlinks
install -m 755 -pDt %{buildroot}/%{_javadir}/%{appfolder}/help target/help/*
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder}/help help/*.html 
install -m 755 -pDt %{buildroot}/%{_javadir}/%{appfolder}/util util/fmguiclear.sh
install -m 755 -pDt %{buildroot}/%{_javadir}/%{appfolder}/util util/postsetup.sh
install -m 644 -pDt %{buildroot}/%{_javadir}/%{appfolder}/util util/ClearFMGUICache.desktop
install -m 755 -pD  install/opa-fmgui.sh %{buildroot}/%{_bindir}/opa-fmgui
install -m 644 -pDt %{buildroot}/%{_sysconfdir}/profile.d install/fmguivars.sh
install -m 644 -pDt %{buildroot}/%{_sysconfdir}/xdg/menus/applications-merged install/Fabric.menu
install -m 644 -pDt %{buildroot}%{_datadir}/desktop-directories install/Fabric.directory
for file in $(find install/images -type f); do
    install -m 644 -pDt %{buildroot}/%{_datadir}/icons/hicolor $file
done
desktop-file-install --dir=%{buildroot}/%{_datadir}/applications install/fmgui.desktop

%post
/bin/touch --no-create %{_datadir}/icons/hicolor &>/dev/null || :
%{_javadir}/%{appfolder}/bin/buildlinks link


%postun
if [ $1 -eq 0 ] ; then
    /bin/touch --no-create %{_datadir}/icons/hicolor &>/dev/null
    /usr/bin/gtk-update-icon-cache %{_datadir}/icons/hicolor &>/dev/null || :
fi

%posttrans
/usr/bin/gtk-update-icon-cache %{_datadir}/icons/hicolor &>/dev/null || :

%files
%{_javadir}/%{appfolder}
%{_javadir}/%{appfolder}/THIRD-PARTY-README
%{_javadir}/%{appfolder}/README
%{_bindir}/opa-fmgui
%{_datadir}/applications/fmgui.desktop
%{_datadir}/desktop-directories/Fabric.directory
%{_datadir}/icons/hicolor
%config(noreplace) %{_sysconfdir}/xdg/menus/applications-merged/Fabric.menu
%config(noreplace) %{_sysconfdir}/profile.d/fmguivars.sh
%license %{_javadir}/%{appfolder}/LICENSE
%license %{_javadir}/%{appfolder}/gritty/gritty_license.txt

%changelog
* Mon Jun 06 2016 Rick Tierney <rick.tierney@intel.com> 10.0.0.0.3-3
- Removed 3rd party jar files, libraries are installed at runtime using Requires
- Moved licenses from the lic folder to the license folder
  
* Thu Jun 02 2016 Rick Tierney <rick.tierney@intel.com> 10.0.0.0.3-2
- Updated to fix license issues

* Wed Apr 06 2016 Robert Amato <robert.amato@intel.com> 10.0.0.0.3-1
- Remove Intel branding
