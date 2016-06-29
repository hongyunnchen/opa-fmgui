#!/bin/bash

# Copyright (c) 2015, Intel Corporation
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
#     * Redistributions of source code must retain the above copyright notice,
#       this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above copyright
#       notice, this list of conditions and the following disclaimer in the
#       documentation and/or other materials provided with the distribution.
#     * Neither the name of Intel Corporation nor the names of its contributors
#       may be used to endorse or promote products derived from this software
#       without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
# FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
# DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
# OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THISDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo Copying $THISDIR/fmguiclear.sh to $HOME/.Intel/FabricManagerGUI
mkdir -p $HOME/.Intel/FabricManagerGUI
\cp -f $THISDIR/fmguiclear.sh $HOME/.Intel/FabricManagerGUI/fmguiclear.sh
echo Copying $THISDIR/ClearFMGUICache.desktop to $HOME/.local/share/applications
mkdir -p $HOME/.local/share/applications
\cp -f $THISDIR/ClearFMGUICache.desktop $HOME/.local/share/applications/ClearFMGUICache.desktop
chmod 744 $HOME/.local/share/applications/ClearFMGUICache.desktop
mkdir -p $HOME/.local/share/desktop-directories
cp -a /usr/share/desktop-directories/Fabric.directory $HOME/.local/share/desktop-directories/Fabric.directory
mkdir -p $HOME/.config/menus/applications-merged
cp -a /etc/xdg/menus/applications-merged/Fabric.menu $HOME/.config/menus/applications-merged/Fabric.menu



