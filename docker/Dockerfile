FROM alpine:edge

MAINTAINER Trevor Johnson "inboxtrevorjohnson@gmail.com"

RUN apk add --no-cache openjdk8

COPY resources/UnlimitedJCEPolicyJDK8/* \
  /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/