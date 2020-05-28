public class Complex {
    private double real, imag;
    Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }
    public double abs() { return real * real + imag * imag; }

    public  Complex sum(Complex c) {
        return new Complex(this.real + c.real, this.imag + c.imag);
    }

    public Complex times(Complex c) {
        double real = this.real * c.real - this.imag * c.imag;
        double imag = this.real * c.imag + this.imag * c.real;
        return new Complex(real,imag);
    }
}