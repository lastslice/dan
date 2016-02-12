#!/bin/bash
set -xe

# Install pip
if ! [ `command -v pip` ]
then
  sudo easy_install pip
fi

# Create virtual env
VENV=.venv
if ! [ `command -v virtualenv` ]
then
	sudo pip2.7 install virtualenv
fi
sudo rm -rf $VENV
virtualenv $VENV -p /usr/bin/python2.7
source $VENV/bin/activate

# Install dependencies
pip2.7 install --download-cache=cache -r dan.pip

cat >> $VENV/bin/activate << EOF
export SPARK_HOME=$SPARK_HOME
export PYTHONPATH=$SPARK_HOME/python:$SPARK_HOME/python/build:$PYTHONPATH
export PATH=$SPARK_HOME/bin:$PATH
EOF

echo "$VENV/bin/activate"
