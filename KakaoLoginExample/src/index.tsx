import {AppRegistry, Platform} from 'react-native';

import App from './App';

AppRegistry.registerComponent('KakaoLoginExample', () => App);

if (Platform.OS === 'web') {
  AppRegistry.runApplication('KakaoLoginExample', {
    rootTag: document.getElementById('root'),
  });
}

export default App;
