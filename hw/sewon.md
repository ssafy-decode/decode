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