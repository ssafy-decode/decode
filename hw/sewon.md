# 24.01.08 - React.js 스터디 발표용 필기

### React.js란?

- 자바스크립트 라이브러리.

- 즉각적인 반응이 가능하며, 페이지 전환이 매우 자연스러워 모바일과 유사한 형태를 보임.

    - 자바스크립트가 데이터를 가져와 업데이트하기 때문.

- 그렇다면 순수 자바스크립트만 사용하지 않고, 리액트를 사용하는 이유는 무엇인가?

    - 자바스크립트는 다루기 힘들어 번거롭고, 오류가 발생하기 쉬우며, 관리 및 수정이 어렵기 때문.

    - 특히 넷플릭스 등의 웹앱은 리액트 없이 바닐라 자바스크립트만으로 구현하는 것은 매우 비현실적.

- 코드샌드박스란?

    - 브라우저에서 실행하는 클라우드 기반의 코드 편집기 겸 개발 환경.

    - 별도 설치 없이 리액트 프로젝트 작업 등이 가능.

### React vs. Javascript

- 리액트 코드는 바닐라 자바스크립트 코드와는 달리:

    - index.html 파일 끝 부분에 내용이 거의 없음 <br/>
    (`<body>` 섹션 끝에 `<div id = “root”></div>`뿐).
    - index.js 파일에서 본격적인 내용이 시작된다고 봄.
    - App.js 파일 안의 `App()` 함수에 html 코드가 적혀 있음.
    - html 코드와 JS 코드를 섞어 쓰며 그 html 코드 안에 동적 요소들이 작성됨.
    - 코드를 선언형으로 작성해 목표할 UI 상태를 정의하고 이에 거쳐야 할 단계는 정의하지 않음 <br/>
    (자바스크립트의 경우: 코드를 명령형으로 작성해 목표가 아니라 거쳐야 할 단계를 정의함).
    - 리액트 내부에서 자바스크립트로 UI를 자동 업데이트해줌.

### 새 React 프로젝트 생성

- URL창에 react.new를 입력하면 코드샌드박스의 작용으로 브라우저에 새 리액트 프로젝트를 바로 생성할 수 있음.

    - 물론 본인의 컴퓨터에서 직접 코드 편집기를 이용해 코딩할 수도 있긴 함 (예를 들어, vs code). 

    - 그러나 node.js 설치가 함께 요구됨. 

    - Vite 등의 로컬 리액트 프로젝트를 생성할 때 이용되는 도구들이 node.js에 있기 때문.

    - Vite 외에도 Create React App이 또 다른 대표적인 도구에 속함.

- 만약 Vite를 쓴다면 
    - npm create vite@latest react-project처럼 명령을 입력해서 리액트 프로젝트를 생성할 수 있고,

    - 작업물에 사용된 추가 패키지를 설치하기 위해 npm install을 해주고,

    - 실행할 때에는 npm run dev를 입력해줘야 함.

- 반면, 코드샌드박스를 이용하면 
    - 위의 명령어들이 자동으로 이미 되어있는 상태이며,

    - 우측에 프리뷰 화면이 함께 뜨기 때문에 곧바로 확인이 가능함.

- 리액트 코드는 JSX를 이용하나, 브라우저에서는 오류가 뜨기 때문에 JSX가 없는 자바스크립트 코드로 변환할 필요가 있음.

    - 이러한 이유로 Vite 등 추가 도구들이 필요해지는 것.



# 24.01.09 - VS Code 및 Intellij 세팅하기

### VS Code

- Node.js 설치
    - npm 명령어를 쓸 수 있게 하기 위함
    - [https://nodejs.org/en/download](https://nodejs.org/en/download)에서 LTS 윈도우 버전을 다운로드
    - Node.js 최신 버전인지 확인
        
        ```bash
        node -v 
        #v20.10.0
        ```
        
    - npm 최신 버전인지 확인
        
        ```bash
        npm -v
        #10.2.3
        ```
        
- Vue3으로 설치
    
    ```bash
    npm install -g @vue/cli
    ```
    
    - Vue 버전이 맞는지 확인. Vuetify 3에는 vue-cli 5.0이 필요
        
        ```bash
        vue -V
        #@vue/cli 5.0.8
        
        vue --version
        #@vue/cli 5.0.8
        ```
        
- Extension 설치
    - Vue 3 Snippets
    - Vetur
    - HTML CSS Support
    - Live Server
    - Code Runner
    - Prettier
        - Prettier를 다운로드
        - Ctrl + , 키로 설정 창을 오픈
        - Editor: Default Formatter 검색 후 Prettier - Code Formatter 선택
        - editor format on save 검색 후 체크박스 체크
        - Prettier 검색 후 Print Width (120), Semi (체크), Single Quote (체크), Tab Width (4), Trailing Comma (es5)로 설정
        (설정은 얼마든지 변경 가능하며, 특정 파일만 변경하고 싶을 경우 .prettierrc 파일을 만들어 루트 디렉토리 안에 추가)
            
            ```bash
            {
              "printWidth": 120,
              "tabWidth": 4,
              "singleQuote": true,
              "trailingComma": "all",
              "semi": false
            }
            
            #.prettierrc
            ```
            
            ```bash
            {
              "semi": true,
              "useTabs": false,
              "tabWidth": 2,
              "trailingComma": "all",
              "printWidth": 120,
              "proseWrap": "never",
              "singleQuote": true,
              "htmlWhitespaceSensitivity": "css",
              "jsxSingleQuote": true,
              "endOfLine": "lf"
            }
            
            #.prettierrc
            ```
            
    - vscode-icons
- 추가로 Vuetify 3 설치
    
    ```bash
    npm i vuetify
    vue add vuetify
    ```
    
    - Choose a preset에서 Vuetify 3 - Vue CLI (preview)를 선택
- 생성한 프로젝트 경로로 이동해서 테스트 실행해보기
    
    ```bash
    npm run serve
    ```
    

### Intellij

- https://github.com/google/styleguide에서 intellij-java-google-style.xml을 다운로드
- 인텔리제이에서 새 프로젝트를 생성 (Language: Java, Build System: Gradle, JDK: 17 Azul Zulu, Gradle DSL: Groovy)
- File > Settings > Code Style
- Java > Scheme에서 톱니바퀴를 누르고 Import Scheme > Intellij IDEA code style scheme
- 아까 다운로드 받았던 intellij-java-google-style.xml을 선택
- Tab size (4), Indent (4), Continuation indent (8)로 설정 후 Apply
(설정은 얼마든지 변경 가능)



# 24.01.10 - ERD 작성 및 React.js 스터디 실습 일부 진행

### ERD

![code Agile](https://github.com/serethia/git-test/assets/137035446/2f332886-413c-4fb6-988c-3900139270b1)

### React.js 스터디 코딩 연습 1: 연습: 함수 다루기

<br/>

![연습1-1](https://github.com/serethia/web1/assets/137035446/44a2e1ae-11e2-4c8a-a628-10fe524db7c0)

<br/>

![연습1-2](https://github.com/serethia/web1/assets/137035446/f16c455c-5928-4217-8f04-83e0ed21fc7e)

<br/>

- 해답

![연습1 해답](https://github.com/serethia/web1/assets/137035446/cd40b9b0-d19c-407e-a059-c76b541df04f)

### React.js 스터디 퀴즈 1: 컴포넌트와 JSX

<br/>

![퀴즈1](https://github.com/serethia/git-test/assets/137035446/5c9bdb02-9623-44a7-9bfe-75504e40d266)

<br/>

![정답1](https://github.com/serethia/git-test/assets/137035446/dcad69f5-936a-45e1-8a86-502f0631d7f8)

<br/>

![퀴즈2](https://github.com/serethia/git-test/assets/137035446/574802c2-9dae-4509-a7fd-5de9b101de35)

<br/>

![정답2](https://github.com/serethia/git-test/assets/137035446/4923133a-e079-459e-8118-0f637e28d1ff)

<br/>

![퀴즈3](https://github.com/serethia/git-test/assets/137035446/5c5b6c31-d49b-451a-8da2-8084bb703ecc)

<br/>

![정답3](https://github.com/serethia/git-test/assets/137035446/4d1529b4-5f70-49aa-9bc3-787e67c74c8e)

<br/>

![퀴즈4](https://github.com/serethia/git-test/assets/137035446/e8dcf59d-f097-455e-b66a-f455adf03cf4)

<br/>

![정답4](https://github.com/serethia/git-test/assets/137035446/b99db2f5-bd61-4431-929f-69f6570235fd)

<br/>

![퀴즈5](https://github.com/serethia/git-test/assets/137035446/609b76ec-a536-4c48-bd28-04e6c562e0ff)

<br/>

![정답5](https://github.com/serethia/git-test/assets/137035446/73849c08-319c-42ca-9112-4d9a452c907e)

<br/>

![퀴즈6](https://github.com/serethia/git-test/assets/137035446/e1906885-a4eb-475d-840f-50000711939d)

<br/>

![정답6](https://github.com/serethia/git-test/assets/137035446/6d8ef5e1-6fa3-44f4-9031-8f0498e52132)

<br/>

![퀴즈7](https://github.com/serethia/git-test/assets/137035446/b1115d9f-04ba-44a7-828c-ae9914e9bed2)

<br/>

![정답7](https://github.com/serethia/git-test/assets/137035446/bfaac194-44d0-47ab-8e68-84b97ba93856)



# 24.01.11 - ERD 수정 추가 및 .

### ERD 업데이트

![code Agile](https://github.com/serethia/git-test/assets/137035446/7e7f50f2-648f-4206-b1bc-4ade47886db3)



# 24.01.12 - .

### .

.
