# TODAY'S MISSION (TOMI)

**모바일 프로그래밍 과제 | 정보보호학과 20251798 천태영**

---

## 프로젝트 개요

> Let's grow your life together.

**TODAY'S MISSION(TOMI/투미)** 는 무기력하거나 우울한 상태가 지속될 때 아무것도 하지 않게 되는 악순환을 끊기 위해 기획된 습관 형성 앱이다.
사용자가 매일 작은 행동이라도 실천할 수 있도록 유도하면서 성취 경험을 통해 점진적인 동기부여를 제공하는 것을 목표로 한다.

- **프로젝트명** : TODAY'S MISSION (TOMI / 투미)
- **개발 유형** : 개인
- **대상 사용자** : 규칙적인 생활 습관을 형성하고 싶은 대학생 및 청년층
- **원본 디자인** : [Figma — Nature-themed Habit Tracker](https://www.figma.com/make/fi6GRsbURczYNPLEXdWYaS/Nature-themed-Habit-Tracker)

---

## 구현된 기능

| 구분 | 기능 | 설명 |
|------|------|------|
| 핵심 기능 | 일일 미션 제공 | 8개의 미션 중 앱 실행 시 랜덤으로 1개 표시 (하드코딩, AI 연동 미구현) |
| 핵심 기능 | 스트릭(Streak) 시스템 | 미션 완료 버튼 클릭 시 연속 달성일 +1, DataStore에 영구 저장 |
| 핵심 기능 | 일상 To-Do 리스트 | 태스크 추가 / 수정 / 삭제 / 완료 체크, Room DB에 영구 저장 |
| 부가 기능 | 진행률 바 | 완료된 태스크 수 기준으로 퍼센트 표시 및 애니메이션 |
| 부가 기능 | 계절 테마 | 봄 / 여름 / 가을 / 겨울 4가지 컬러 테마 선택, DataStore에 저장 |
| 부가 기능 | 언어 선택 | 영어 / 스페인어 / 프랑스어 선택 저장 (UI 텍스트 실시간 번역 미적용) |

> **미구현 사항** : AI API 연동, 알림(Notification), 다크 모드, 홈 화면 위젯

---

## 앱 화면 구성

```
[메인 화면 — HomeScreen]
  - 스트릭 카운터 표시 (DataStore에서 불러옴)
  - 오늘의 미션 카드 (8개 중 랜덤 1개)
  - "Success! I did it" 버튼 클릭 시 미션 완료 처리 + 스트릭 +1
  - "My Daily Garden" 버튼으로 세부 화면 이동
        |
        v
[세부 화면 — DailyGardenScreen]
  - 전체 태스크 진행률 바
  - 태스크 목록 (Room DB에서 Flow로 실시간 수신)
  - 태스크 추가 (텍스트 입력 + Leaf / Sun / Droplet 아이콘 선택)
  - 태스크 수정 / 삭제
  - 원형 체크박스로 완료 토글
  - 전체 완료 시 Garden Complete 배너 표시
        |
        v
[설정 화면 — SettingsScreen]
  - 언어 선택 (EN / ES / FR) — 선택값만 저장, 텍스트 번역 미적용
  - 계절 테마 선택 (Spring / Summer / Fall / Winter) — 즉시 전체 UI 색상 변경
```

---

## 프로젝트 구조

```
NatureHabitTracker/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/nature/habittracker/
│           ├── MainActivity.kt                      # 앱 진입점, NavHost 설정
│           ├── AppViewModel.kt                      # 전체 UI 상태 관리 (MVVM)
│           ├── Screen.kt                            # 화면 라우트 상수 정의
│           ├── data/
│           │   ├── Task.kt                          # Task Entity (id, text, completed, icon, createdAt)
│           │   ├── TaskDao.kt                       # Room DAO (getAllTasks, insert, update, delete, toggle)
│           │   ├── TaskRepository.kt                # DB 접근 레이어 + 기본 태스크 8개 정의
│           │   ├── AppDatabase.kt                   # Room DB 싱글톤 + TypeConverter
│           │   ├── Season.kt                        # Season / Language 열거형 + SeasonalThemes 색상 정의
│           │   └── UserPreferencesRepository.kt     # DataStore로 season / language / streak 저장
│           └── ui/
│               ├── SharedComponents.kt              # TaskIconImage, BotanicalBackground, missions 리스트
│               ├── home/
│               │   └── HomeScreen.kt               # 미션 카드, 스트릭, 가든 이동 버튼
│               ├── garden/
│               │   └── DailyGardenScreen.kt        # 태스크 목록, 추가 폼, 진행률 바
│               ├── settings/
│               │   └── SettingsScreen.kt           # 언어 선택, 계절 테마 선택
│               └── theme/
│                   └── Theme.kt                    # Compose MaterialTheme 커스텀 설정
├── gradle/
│   ├── libs.versions.toml                          # 의존성 버전 통합 관리 (Version Catalog)
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle
└── settings.gradle
```

---

## 사용 기술

### Presentation Layer

| 라이브러리 | 버전 | 용도 |
|------------|------|------|
| Jetpack Compose BOM | 2024.09.00 | 선언형 UI |
| Material3 | BOM 포함 | 디자인 시스템, Shape, Typography |
| Material Icons Extended | BOM 포함 | Spa, WbSunny, Opacity 등 아이콘 |
| Compose Animation API | BOM 포함 | AnimatedContent, animateFloatAsState 등 |
| Navigation Compose | 2.8.1 | 화면 간 이동 (NavHost / NavController) |

### Storage Layer

| 라이브러리 | 버전 | 용도 |
|------------|------|------|
| Room Runtime + KTX | 2.6.1 | 태스크 로컬 저장 (SQLite) |
| DataStore Preferences | 1.1.1 | season, language, streak 설정값 저장 |
| GSON | 2.10.1 | 의존성 포함 (직접 사용 없음, Room TypeConverter는 Enum으로 처리) |

### 기타

| 라이브러리 | 버전 | 용도 |
|------------|------|------|
| Kotlin Coroutines Android | 1.8.1 | 비동기 DB / DataStore 처리 |
| Kotlin Flow (StateFlow) | Coroutines 포함 | ViewModel → UI 단방향 데이터 흐름 |
| ViewModel (AAC) | 2.8.5 | UI 상태 관리 및 생명주기 분리 |
| Activity Compose | 1.9.2 | ComponentActivity + setContent |

> Retrofit, OkHttp, Coil은 제안서에 명시되어 있으나 현재 버전에서는 미구현 상태이다.

---

## 아키텍처

MVVM(Model-View-ViewModel) 패턴 기반으로 단방향 데이터 흐름을 구현했다.

```
View (HomeScreen / DailyGardenScreen / SettingsScreen)
    |  collectAsState()
    v
AppViewModel
    |  StateFlow<List<Task>>, StateFlow<Season>, StateFlow<Int> (streak) ...
    v
Repository (TaskRepository / UserPreferencesRepository)
    |
    v
Data Source (Room DB — tasks 테이블 / DataStore — user_prefs)
```

- `AppViewModel`이 `TaskRepository`와 `UserPreferencesRepository`를 모두 보유하며 단일 진입점 역할을 한다.
- UI는 ViewModel의 StateFlow를 `collectAsState()`로 구독하고, 사용자 이벤트(addTask, toggleTask, setSeason 등)만 ViewModel로 전달한다.
- Room은 Flow를 반환하므로 DB 변경 시 UI가 자동으로 갱신된다.

---

## 개발 환경

| 항목 | 버전 |
|------|------|
| Android Studio | Hedgehog 2023.1.1 이상 |
| Kotlin | 2.0.0 |
| AGP (Android Gradle Plugin) | 8.6.0 |
| JDK | 17 |
| Gradle | 8.7 |
| Min SDK | API 26 (Android 8.0 Oreo) |
| Target SDK | API 35 |

---

## 실행 방법

1. ZIP 파일을 압축 해제하거나 저장소를 클론한다.

```bash
git clone https://github.com/{username}/NatureHabitTracker.git
```

2. Android Studio에서 프로젝트를 연다.

```
File > Open > NatureHabitTracker 폴더 선택
```

3. Gradle Sync가 완료될 때까지 대기한다. (최초 실행 시 의존성 자동 다운로드, 인터넷 연결 필요)

4. 에뮬레이터 또는 실기기(API 26 이상)를 연결한 후 실행한다.

```
Run > Run 'app'  또는  Shift + F10
```

---

## 디자인 시스템

Figma 원본의 색상값을 `SeasonalThemes` 객체에 그대로 반영했다.  
계절 변경 시 `NatureHabitTrackerTheme`에 season이 전달되어 전체 앱 색상이 즉시 바뀐다.

| 테마명 | Primary | Secondary | Accent | 배경색 |
|--------|---------|-----------|--------|--------|
| Spring Bloom | `#8B9F87` | `#F4A5AE` | `#C9B8A8` | `#F5F3EF` |
| Summer Sun | `#7EB09B` | `#F9D77E` | `#A8C9D4` | `#FEF9F3` |
| Autumn Harvest | `#C17F5B` | `#D4A59A` | `#8B7355` | `#F7F0E8` |
| Winter Frost | `#7A9AA8` | `#B8A8C9` | `#A8B8B8` | `#F2F4F6` |

---

## Figma → Android 변환 시 주요 대응

| 웹 (React + Tailwind) | Android (Kotlin + Compose) |
|-----------------------|---------------------------|
| `useState` / Context API | ViewModel + StateFlow |
| `localStorage` | Room DB (태스크) + DataStore (설정) |
| `rounded-[32px]` | `RoundedCornerShape(32.dp)` |
| Framer Motion 애니메이션 | `animateFloatAsState`, `AnimatedContent` |
| `react-router` 네비게이션 | Navigation Compose (NavHost) |
| 랜덤 미션 (하드코딩 배열) | `missions.random()` (동일 방식 유지) |

---

## 향후 개선 사항

- [ ] Retrofit + AI API 연동을 통한 실제 일일 미션 자동 생성
- [ ] 알림 기능 구현 (WorkManager + NotificationManager)
- [ ] 다국어 실제 번역 적용 (strings.xml 다국어 리소스)
- [ ] 홈 화면 위젯 지원
- [ ] 다크 모드 지원
- [ ] 미션 완료 히스토리 및 통계 화면 추가

---

## 참고 자료

- [Jetpack Compose 공식 문서](https://developer.android.com/jetpack/compose)
- [Room 공식 문서](https://developer.android.com/training/data-storage/room)
- [DataStore 공식 문서](https://developer.android.com/topic/libraries/architecture/datastore)
- [Navigation Compose 공식 문서](https://developer.android.com/guide/navigation/navigation-compose)
- [Figma 원본 디자인](https://www.figma.com/make/fi6GRsbURczYNPLEXdWYaS/Nature-themed-Habit-Tracker)

---

정보보호학과 20251798 천태영
