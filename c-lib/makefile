LIBTOOL=libtool
CC = cc
CFLAGS = -std=c99 -pedantic -Wall
SRCDIR = $(PROJECTPATH)/src
BINDIR = $(PROJECTPATH)/bin
PROJECTPATH = .
CINTEROPPATH = $(PROJECTPATH)/../mpp-library/build/cinterop

all: Bindir multiplier.o
	$(LIBTOOL) -static $(BINDIR)/multiplier.o -o $(BINDIR)/libmultiplier.a
	mv $(BINDIR)/libmultiplier.a $(CINTEROPPATH)
	cp $(PROJECTPATH)/include/multiplier.h $(CINTEROPPATH)

multiplier.o: Bindir
	$(CC) $(CFLAGS) -c $(SRCDIR)/multiplier.c -o $(BINDIR)/multiplier.o

Bindir:
	mkdir -p $(BINDIR)
	mkdir -p $(CINTEROPPATH)

clean:
	rm -r $(BINDIR)
	rm -r $(CINTEROPPATH)
