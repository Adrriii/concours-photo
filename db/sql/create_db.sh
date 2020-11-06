#!/bin/sh

exec_script() 
{
    mysql -u root -p photodb < "/var/db/$@" > example
}

exec_script db.sql

exec_script user.sql