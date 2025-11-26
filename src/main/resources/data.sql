-- 더미 사용자 데이터
-- 비밀번호는 모두 "password123" (BCrypt 암호화된 값)
INSERT INTO users (id, username, password, created_at, updated_at) VALUES
(1, 'user1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'user2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'user3', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 더미 리뷰 데이터
INSERT INTO reviews (id, book_title, content, rating, user_id, created_at, updated_at) VALUES
-- user1의 리뷰들
(1, '자바의 정석', '자바를 처음 배우는 사람에게 추천하는 책입니다. 기초부터 고급까지 상세하게 설명되어 있어요.', 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '클린 코드', '코드를 작성하는 방법뿐만 아니라 좋은 코드를 작성하는 철학을 배울 수 있었습니다.', 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '이펙티브 자바', '자바 개발자라면 꼭 읽어야 할 필독서입니다. 조금 어렵지만 많은 도움이 되었어요.', 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- user2의 리뷰들
(4, '스프링 부트 입문', '스프링 부트를 처음 시작하기에 좋은 책입니다. 실습 위주로 구성되어 있어요.', 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, '토비의 스프링', '스프링의 깊은 이해를 위한 최고의 책입니다. 두껍지만 천천히 읽어볼 가치가 있어요.', 5, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, '리팩토링 2판', '코드를 개선하는 다양한 기법을 배울 수 있습니다. 예제가 풍부해서 좋았어요.', 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- user3의 리뷰들
(7, '오브젝트', '객체지향 설계에 대한 통찰력을 얻을 수 있는 책입니다.', 5, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'HTTP 완벽 가이드', 'HTTP 프로토콜에 대해 상세하게 설명하는 책입니다. 웹 개발자 필독서!', 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, '가상 면접 사례로 배우는 대규모 시스템 설계 기초', '시스템 설계에 대한 전반적인 이해를 돕는 책입니다. 면접 준비에도 좋아요.', 5, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, '데이터베이스 첫걸음', '데이터베이스 기초 개념을 쉽게 설명해주는 입문서입니다.', 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 추가 리뷰들
(11, 'Do it! 알고리즘 코딩 테스트', '알고리즘 문제 풀이에 도움이 되는 책입니다. 자바 예제로 설명되어 있어요.', 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, '만들면서 배우는 클린 아키텍처', '실무에 적용 가능한 클린 아키텍처 패턴을 배울 수 있습니다.', 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Real MySQL 8.0', 'MySQL의 내부 동작 원리를 깊이 있게 다루는 책입니다. 고급 개발자에게 추천!', 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, '모던 자바 인 액션', '자바 8 이후의 최신 기능들을 상세히 설명하는 책입니다.', 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'DDD Start!', '도메인 주도 설계의 기본 개념을 쉽게 설명해주는 입문서입니다.', 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ID 시퀀스를 다음 값으로 초기화
ALTER TABLE users ALTER COLUMN id RESTART WITH 4;
ALTER TABLE reviews ALTER COLUMN id RESTART WITH 16;
