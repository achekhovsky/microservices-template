FROM docker.elastic.co/logstash/logstash:8.7.0

RUN rm -f /usr/share/logstash/pipeline/logstash.conf

COPY pipeline.conf /usr/share/logstash/pipeline/pipeline.conf
COPY logstash.conf /usr/share/logstash/config/logstash.conf


