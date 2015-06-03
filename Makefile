.PHONY: \
	help \
	clean \
	run \
	test \
	stage \
	docker-build \
	nuke

help:
	@echo "Usage: make test|build|run|nuke"
	@echo
	@echo "    clean         Clean docview"
	@echo "    run           Run docview"
	@echo "    test          Run docview tests"
	@echo
	@echo "    docker-build  Build the docview Docker image"
	@echo
	@echo "    nuke          Clean ignored files"
	@echo

clean:
	sbt clean

test:
	sbt run

test:
	sbt test

stage: clean test
	sbt stage

docker-build: test stage
	docker build -t docview:latest .

nuke:
	git clean -Xdf
