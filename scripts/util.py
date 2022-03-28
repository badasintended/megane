import sys
from os.path import dirname, realpath


def get_base_dir():
    return dirname(dirname(realpath(sys.argv[0])))
