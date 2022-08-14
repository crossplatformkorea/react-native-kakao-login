import {
  KakaoOAuthToken,
  KakaoProfile,
  getProfile as getKakaoProfile,
  login,
  logout,
  unlink,
  KakaoProfileNoneAgreement,
} from "@react-native-seoul/kakao-login";
import { StatusBar } from "expo-status-bar";
import React, { useEffect, useState } from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";

export default function App() {
  const [result, setResult] = useState<string>("");

  useEffect(() => {
    console.log(result);

    // return () => {
    //   second
    // }
  }, [result]);

  const signInWithKakao = async (): Promise<void> => {
    try {
      const token: KakaoOAuthToken = await login();

      setResult(JSON.stringify(token));
    } catch (e) {
      console.log(e);
    }
  };

  const signOutWithKakao = async (): Promise<void> => {
    try {
      const message = await logout();

      setResult(message);
    } catch (e) {
      console.log(e);
    }
  };

  const getProfile = async (): Promise<void> => {
    try {
      const profile: KakaoProfile | KakaoProfileNoneAgreement =
        await getKakaoProfile();

      setResult(JSON.stringify(profile));
    } catch (e) {
      console.log(e);
    }
  };

  const unlinkKakao = async (): Promise<void> => {
    try {
      const message = await unlink();

      setResult(message);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <View style={styles.container}>
      <StatusBar style="auto" />
      <TouchableOpacity onPress={() => signInWithKakao()} style={styles.button}>
        <Text style={styles.buttonText}>카카오 로그인</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={() => getProfile()} style={styles.button}>
        <Text style={styles.buttonText}>프로필 조회</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={() => unlinkKakao()} style={styles.button}>
        <Text style={styles.buttonText}>링크 해제</Text>
      </TouchableOpacity>
      <TouchableOpacity
        onPress={() => signOutWithKakao()}
        style={styles.button}
      >
        <Text style={styles.buttonText}>카카오 로그아웃</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  button: {
    height: 30,
    width: 200,
    backgroundColor: "#FEE500",
    borderRadius: 40,
    borderWidth: 1,
    justifyContent: "center",
    marginVertical: 10,
  },
  buttonText: {
    alignSelf: "center",
  },
});
