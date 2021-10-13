package templateCallback.framework;

import java.sql.Connection;

//오직 하나의 '추상 메서드만'을 가져야 한다.(다른 메서드들은 상관x)
@FunctionalInterface
public interface ConnectionMaker {

	Connection getConnection();
	
}
