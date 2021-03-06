package test01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Car {
	@Autowired //타입을 기준으로 빈을 찾아서 의존성 주입
	@Qualifier(value ="wheelBean2")  //wheel이란 빈객체가 두개 이상이어서 의존성주입이 실패할 경우 Qualifier를 이용해 지정해줄 수 있다.
	private Wheel wheel;
	
	public Car() {
		// TODO Auto-generated constructor stub
	}

	public Car(Wheel wheel) {
		super();
		this.wheel = wheel;
	}

	public Wheel getWheel() {
		return wheel;
	}

	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}

	@Override
	public String toString() {
		return "Car [wheel=" + wheel + "]";
	}
}
