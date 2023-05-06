#!/bin/bash

#
# Script to create MySQL db + user.
#
# @author   diego prada 
# @website  
# @version  1.0

SQL1="CREATE DATABASE IF NOT EXISTS ${DBUP};"
mysql -u root -p$MARIADB_ROOT_PASSWORD -e "${SQL1}"
