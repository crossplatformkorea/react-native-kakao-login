import React, { Component } from 'react';
import {
  Platform,
  Alert,
  StyleSheet,
  Text,
  View,
  NativeModules,
} from 'react-native';

// const { RNKakaoLogins } = NativeModules;
import RNKakaoLogins from 'react-native-kakao-logins';
import NativeButton from 'apsl-react-native-button';

export default class App extends Component<}{}> {
  constructor(props) {
    super(props);
    this.state = {
      isKakaoLogging: false,
      token: 'token has not fetched',
    };
    if (!RNKakaoLogins) {
      console.log('Not Linked');
    }
  }

  // 카카오 로그인 시작.
  kakaoLogin() {
    console.log('   kakaoLogin   ');
    RNKakaoLogins.login((err, result) => {
      if (err){
        Alert.alert('error', err.toString());
        return;
      }
      Alert.alert('result', result);
    });
  }

  kakaoLogout() {
    console.log('   kakaoLogout   ');
    RNKakaoLogins.logout((err, result) => {
      if (err){
        Alert.alert('error', err.toString());
        return;
      }
      Alert.alert('result', result);
    });
  }

  // 로그인 후 내 프로필 가져오기.
  getProfile() {
    console.log('getKakaoProfile');
    RNKakaoLogins.getProfile((err, result) => {
      if (err){
        Alert.alert('error', err.toString());
        return;
      }
      Alert.alert('result', result);
    });
  }

  render() {
    return (
      <View style={ styles.container }>
        <View style={ styles.header }>
          <Text>LOGIN</Text>
        </View>
        <View style={ styles.content }>
          <NativeButton
            isLoading={this.state.isNaverLoggingin}
            onPress={() => this.kakaoLogin()}
            activeOpacity={0.5}
            style={styles.btnKakaoLogin}
            textStyle={styles.txtNaverLogin}
          >LOGIN</NativeButton>
          <Text>{this.state.token}</Text>
          <NativeButton
            onPress={() => this.kakaoLogout()}
            activeOpacity={0.5}
            style={styles.btnKakaoLogin}
            textStyle={styles.txtNaverLogin}
          >Logout</NativeButton>
          <NativeButton
            isLoading={this.state.isKakaoLogging}
            onPress={() => this.getProfile()}
            activeOpacity={0.5}
            style={styles.btnKakaoLogin}
            textStyle={styles.txtNaverLogin}
          >getProfile</NativeButton>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    marginTop: Platform.OS === 'ios' ? 0 : 24,
    paddingTop: Platform.OS === 'ios' ? 24 : 0,
    backgroundColor: 'white',
  },
  header: {
    flex: 8.8,
    flexDirection: 'row',
    alignSelf: 'stretch',
    justifyContent: 'center',
    alignItems: 'center',
  },
  content: {
    flex: 87.5,
    flexDirection: 'column',
    justifyContent: 'center',
    alignSelf: 'stretch',
    alignItems: 'center',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
  },
  btnKakaoLogin: {
    height: 48,
    width: 240,
    alignSelf: 'center',
    backgroundColor: '#F8E71C',
    borderRadius: 0,
    borderWidth: 0,
  },
  txtNaverLogin: {
    fontSize: 16,
    color: '#3d3d3d',
  },
});
