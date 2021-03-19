### ANNOUNCEMENT

DO NOT MODIFY OR CHANGE THE CODE BEFORE CONFIRMED BY `DOOBOOLAB`. THIS REPOSITORY IS USED IN `DOOBOO-CLI`.

# React Native TS Boilerplate

[![codecov](https://codecov.io/gh/dooboolab/dooboo-native-ts/branch/master/graph/badge.svg)](https://codecov.io/gh/dooboolab/dooboo-native-ts)
[![CircleCI](https://circleci.com/gh/dooboolab/dooboo-native-ts.svg?style=svg)](https://circleci.com/gh/dooboolab/dooboo-native-ts) [![Greenkeeper badge](https://badges.greenkeeper.io/dooboolab/dooboo-native-ts.svg)](https://greenkeeper.io/)

> Specification

- [react-native](https://github.com/facebook/react-native)
- [react-navigation](https://github.com/react-navigation/react-navigation)
- [typescript](https://github.com/Microsoft/TypeScript)
- [fbt](https://medium.com/dooboolab/localizing-react-app-with-fbt-instead-of-i18n-90822e0cb373)
- [styled-components](https://github.com/styled-components/styled-components)
- [ts-jest](https://github.com/kulshekhar/ts-jest)
- [react-hook](https://reactjs.org/docs/hooks-intro.html)
- [@testing-library/react-native](https://github.com/callstack/react-native-testing-library)
- [@testing-library/react-hooks](https://github.com/testing-library/react-hooks-testing-library)
- [prettier](https://prettier.io)

### Gain points

```
1. Sample of context-api with `react-hook` (`useContext`).
2. Know how to structure react native app with typescript.
3. Know how to navigate between screens with `react-navigation`.
4. Know how to write test code with `testing-library`.
5. Know how to `lint` your project with `eslint` for both `ts` and maybe some `js`.
6. Know how to localize your project.
```

### INSTALL

```
npm install && npm start
// or
yarn && yarn start
```

### Structures

```text
app/
├─ .doobooo // necessary if using dooboo-cli
├─ assets
│  └─ icons // app icons
│  └─ images // app images like background images
├─ node_modules/
├─ src/
│  └─ apis
│  └─ components
│     └─ navigations
│     └─ screen
│     └─ shared
│  └─ providers
│  └─ utils
│  └─ App.tsx
├─ test/
├─ .buckconfig
├─ .flowconfig
├─ .gitattributes
├─ .gitignore
├─ .watchmanconfig
├─ app.json
├─ babel.config.js
├─ index.js
├─ jest.config.js
├─ package.json
├─ README.md
├─ STRINGS.js
├─ tsconfig.json
└─ eslint.json
```

### Running the project

Running the project is as simple as running

```sh
npm run start
```

This runs the `start` script specified in our `package.json`, and will spawn off a server which reloads the page as we save our files.
Typically the server runs at `http://localhost:8080`, but should be automatically opened for you.

## Testing the project

Testing is also just a command away:

```sh
npm test
```

> Result

```
> jest -u

 PASS  src/components/shared/__tests__/Button.test.tsx
 PASS  src/components/screen/__tests__/Intro.test.tsx
 › 2 snapshots written.

Snapshot Summary
 › 2 snapshots written in 1 test suite.

Test Suites: 2 passed, 2 total
Tests:       5 passed, 5 total
Snapshots:   2 added, 4 passed, 6 total
Time:        3.055s, estimated 6s
Ran all test suites
```

### Writing tests with Jest

We've created test examples with jest-ts in `src/components/screen/__tests__` and `src/components/shared/__tests__`. Since react is component oriented, we've designed to focus on writing test in same level of directory with component. You can simply run `npm test` to test if it succeeds and look more closer opening the source.

### Vscode prettier and eslint setup

```
"eslint.enable": true,
"eslint.validate": [
    "javascript",
    "javascriptreact",
    "typescript",
    "typescriptreact"
],
// prettier extension setting
"editor.formatOnSave": true,
"[javascript]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
},
"[javascriptreact]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
},
"[typescript]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
},
"[typescriptreact]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
},
"prettier.singleQuote": true,
"prettier.trailingComma": "all",
"prettier.arrowParens": "always",
"prettier.jsxSingleQuote": true
```

### Using Context Api

Whenever you add your own Context provider you can add it to `providers/` and use it inside of `providers/index.tsx`

- [Splitting provider is preferred](https://github.com/facebook/react/issues/15156#issuecomment-474590693)

```tsx
// Add providers here
const RootProvider = ({
  initialThemeType,
  children,
}: Props): React.ReactElement => {
  return (
    <AppProvider>
      <ThemeProvider
        initialThemeType={initialThemeType ? ThemeType.LIGHT : ThemeType.DARK}
      >
        {children}
      </ThemeProvider>
    </AppProvider>
  );
};
```

The `RootProvider` is being used at `App.tsx` and test files easily

```tsx
// App.tsx
function App(): React.ReactElement {
  return (
    <RootProvider>
      <SwitchNavigator />
    </RootProvider>
  );
}
```

```tsx
// test files
const component = (props): React.ReactElement => {
  return (
    <RootProvider initialThemeType>
      <Intro {...props} />
    </RootProvider>
  );
};
```

> using consistent theme(ThemeType.LIGHT as default) explicitly is encouraged in testing for avoiding unexpected snapshot test errors

### Localization

Previously, we used `i18n-j` to localize our app and we decided to switch to [fbt](https://github.com/facebook/fbt). If you want to understand why, you may see our blog for [Localizing react app with FBT instead of  src/utils/i18n](https://medium.com/dooboolab/localizing-react-app-with-fbt-instead-of-i18n-90822e0cb373).

We've defined localized strings in `assets/translations/en.json` for English and `assets/translations/ko.json` for Korean. Since the `en` is default locale setup in current project, you do not need to localize this file. However, you still should not delete this if you don't want to see missing localization warning messages when you are running jest.

We are using [fbt](https://github.com/facebook/fbt) to localize our app which is maintained by Facebook team. Simply running `yarn fbt:all` will generate `i18n/fbt/translatedFbts.json` which has all the localized strings.

If you find trouble using it, you may follow [Integrate FBT into your React Native Application](https://medium.com/translate-your-react-native-application-with/integrate-fbt-into-your-react-native-application-2bac420e8e0c).
