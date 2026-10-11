# Headlines - Monzo Android Code Test Solution

Candidate solution for the Headlines Android app test. Built using Clean Architecture, Coroutines, and Jetpack Compose based on Monzo's Engineering Principles.

---

## 📌 Decision Rationale & Answers

### 1. What were your priorities, and why?
With the **4-hour limit** and Monzo's *"Optimise for impact to effort ratio"* principle, priorities were:

1. **Fix Crashes & Bugs (P0)**: Resolved pull-to-refresh infinite loading, unhandled network exceptions, and detail API deserialization models.
2. **Architecture & Coroutines (P1)**: Migrated legacy RxJava to Kotlin Coroutines (`suspend` + `viewModelScope`) and organized the flat package structure into `data`, `domain`, `ui`, and `di`.
3. **Features & Spec Mockup (P2)**: Added Article Detail screen, in-memory Favourites, section headers, and styled card items (`The_Guardian.png`).
4. **Unit Testing (P3)**: Added ViewModel tests using `kotlinx-coroutines-test` (`runTest`) for success and error handling.

---

### 2. Key Decisions & Commit History

| Topic                           | Commit Link                                                                                                            | Rationale                                                                                                  | Monzo Principle                                   |
|:--------------------------------|:-----------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------|:--------------------------------------------------|
| **Base Import**                 | [Initial commit](https://github.com/BlankSnow/monzo-headlines-android/commit/d1384fa795c63d400dd6e6aae28901fdb3e02260) | Imported base codebase and initialised repo.                                                               | *Make changes small, make them often*             |
| **API Key & Build Fixes**       | [Commit Link](https://github.com/BlankSnow/monzo-headlines-android/commit/df4a53a56172c29ced114d6da16cc478f1f01e1d)    | Configured JDK 17, updated Mockito 5 for Java 17 compatibility, and added Coroutines test dependencies.    | *Consider tests as the foundations*               |
| **Clean Arch & Coroutines**     | [Commit Link](https://github.com/BlankSnow/monzo-headlines-android/commit/0e06f1b13f378a58430a76cd37e764bb2a7dd8d8)    | Restructured package into `data`, `domain`, `ui`, `di`. Replaced RxJava with Retrofit `suspend` functions. | *Build systems to be read and debugged by humans* |
| **Pull-to-Refresh Fix & Tests** | [Commit Link](https://github.com/BlankSnow/monzo-headlines-android/commit/bca939c28d53e84b20648e0e115a586bfe03a06c)    | Fixed infinite refresh spinner state. Added ViewModel Coroutines unit tests for success and failure paths. | *Pay attention to unexpected system behaviour*    |

---

### 3. What would you change about the structure of the code?
- **Multi-Module Architecture**: Package separation (`data`, `domain`, `ui`, `di`) works well for this scope. In a production app, I would split this into separate Gradle modules (`:core:data`, `:core:domain`, `:feature:articles`) to enforce boundaries and speed up build times.
- **Dependency Injection**: Replaced manual instantiation with **Koin** for lightweight ViewModel lifecycle management.

---

### 4. If you had another two days, what would you have tackled next?
1. **Persistent Favourites**: Store favourited article IDs in Room DB or DataStore instead of in-memory set.
2. **Offline Support**: Cache articles locally using Room for offline reading.
3. **Paging 3**: Add infinite scroll pagination for the article feed.
4. **UI Tests**: Add Compose UI tests (`compose-test-junit4`) for list rendering and detail navigation.

---

### 5. What bugs did you find but not fix?
- **Strict Date Range Grouping ("Last week")**: The API returns articles chronologically. Exact calendar week grouping was deferred due to time limits.
- **Image Error Placeholders**: Coil image loading lacks fallback placeholder drawables if network images fail to load.

---

### 6. What would you change about the visual design of the app?
- **Material 3**: Migrate from Material 2 to Material 3 with Monzo brand colors.
- **Shimmer Placeholders**: Add shimmer loading skeletons instead of circular progress indicators.
- **Rich HTML Text**: Parse and style body HTML tags using Compose `AnnotatedString`.

---

### 7. Approximately how long you spent on this project
- **Total Time**: ~3.5 hours (planning, refactoring, feature work, tests, and documentation).
