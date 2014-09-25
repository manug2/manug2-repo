#ifndef PAYOFF2_H
#define PAYOFF2_H

class PayOff
{
public:
	PayOff(double Strike_) : Strike(Strike_){}
	virtual double operator()(double Spot) const =0;
	virtual ~PayOff() {}

	double Strike;
private:
};

class PayOffCall : public PayOff
{
public:
	PayOffCall(double Strile_);
	virtual double operator()(double Spot) const;
	virtual ~PayOffCall() {}
	
private:
};

class PayOffPut : public PayOff
{
public:
	PayOffPut(double Strile_);
	virtual double operator()(double Spot) const;
	virtual ~PayOffPut() {}
	
private:
};

#endif

