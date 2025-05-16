FROM ubuntu:latest
LABEL authors="thean"

ENTRYPOINT ["top", "-b"]