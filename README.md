# Steganography 

What is it? [Wikipedia-Steganography](https://en.wikipedia.org/wiki/Steganography)

Essentially it is the act to conceal a file in another file

This small project implements that idea, using an image as a container for a text file

### How did I do it?

Each pixel in an image is represented by three bytes. 
Each byte specifies the intensity level of a primary color: red, green, or blue (RGB). 

The `Stenography.encode()` function reads every bit from the given file and overwrites the least significant bit of each pixel byte, injecting the file bits. 

To indicate the end of the hidden data, an eight-bit sequence is also encoded in the image.

## TODO

- Make it an app

- Add some layer of encryption?