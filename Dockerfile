FROM dockerfile/java:oracle-java8

MAINTAINER eczarny@gmail.com

RUN \
  set -x ; \
  adduser --system --group --no-create-home --disabled-password --disabled-login docview

ADD target/universal/stage /app

RUN \
  set -x ; \
  mkdir -pv /app/logs && \
  chown -R docview /app

USER docview

EXPOSE 9000

VOLUME [ "/app/logs" ]

CMD /app/bin/docview -J-Xms128M -J-Xmx512M
