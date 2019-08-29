import React, {useState} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View,
    Image
} from 'react-native';

import RNKakaoLogins from 'react-native-kakao-logins';
import NativeButton from 'apsl-react-native-button';

if (!RNKakaoLogins) {
    console.error('Module is Not Linked');
}

const logCallback = (log,callback) => {
    console.log(log);
    callback;
};

const TOKEN_EMPTY = "token has not fetched";
const PROFILE_EMPTY = {id:'profile has not fetched', email:'profile has not fetched', profile_image_path:null};

export default App = () => {
    const [loginLoading, setLoginLoading] = useState(false);
    const [logoutLoading, setLogoutLoading] = useState(false);
    const [profileLoading, setProfileLoading] = useState(false);

    const [token, setToken] = useState(TOKEN_EMPTY);
    const [profile, setProfile] = useState(PROFILE_EMPTY);

    const kakaoLogin = () => {
        logCallback('Login Start', setLoginLoading(true));

        RNKakaoLogins.login((err, result) => {
            if (err){
                return logCallback(`Login Failed:${err.toString()}`, setLoginLoading(false));
            }
            setToken(result.token);
            logCallback(`Login Finished:${result.token}`, setLoginLoading(false));
        });
    };

    const kakaoLogout = () => {
        logCallback('Logout Start', setLogoutLoading(true));

        RNKakaoLogins.logout((err, result) => {
            if (err){
                return logCallback(`Logout Failed:${err.toString()}`, setLogoutLoading(false));
            }
            setToken(TOKEN_EMPTY);
            setProfile(PROFILE_EMPTY);
            logCallback(`Logout Finished:${result}`, setLogoutLoading(false));
        });
    };

    const getProfile = () => {
        logCallback('Get Profile Start', setProfileLoading(true));

        RNKakaoLogins.getProfile((err, result) => {
            if (err){
                return logCallback(`Get Profile Failed:${err.toString()}`, setProfileLoading(false));
            }
            setProfile(result);
            logCallback(`Get Profile Finished:${JSON.stringify(result)}`, setProfileLoading(false));
        });
    };

    const {id, email, profile_image_path:photo} = profile;

    return (
        <View style={ styles.container }>
            <View style={styles.profile}>
                <Image
                    style={styles.profilePhoto}
                    source={{uri:photo}}
                />
                <Text>{`id : ${id}`}</Text>
                <Text>{`email : ${email}`}</Text>
            </View>
            <View style={ styles.content}>
                <Text style={styles.token}>{token}</Text>
                <NativeButton
                    isLoading={loginLoading}
                    onPress={kakaoLogin}
                    activeOpacity={0.5}
                    style={styles.btnKakaoLogin}
                    textStyle={styles.txtKakaoLogin}
                >
                    LOGIN
                </NativeButton>
                <NativeButton
                    isLoading={logoutLoading}
                    onPress={kakaoLogout}
                    activeOpacity={0.5}
                    style={styles.btnKakaoLogin}
                    textStyle={styles.txtKakaoLogin}
                >
                    Logout
                </NativeButton>
                <NativeButton
                    isLoading={profileLoading}
                    onPress={getProfile}
                    activeOpacity={0.5}
                    style={styles.btnKakaoLogin}
                    textStyle={styles.txtKakaoLogin}
                >
                    getProfile
                </NativeButton>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        marginTop: Platform.OS === 'ios' ? 0 : 24,
        paddingTop: Platform.OS === 'ios' ? 24 : 0,
        backgroundColor: 'white',
    },
    profile: {
        flex: 4,
        alignItems:'center',
        justifyContent:'flex-end',
    },
    profilePhoto: {
        width:120,
        height:120,
        borderWidth:1,
        borderColor:'black'
    },
    content: {
        flex: 6,
        justifyContent: 'flex-start',
        alignItems: 'center',
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
    },
    token: {
        width:200,
        fontSize:12,
        padding:5,
        borderRadius:8,
        marginVertical:20,
        backgroundColor:'grey',
        color:'white',
        textAlign:'center'
    },
    btnKakaoLogin: {
        height: 48,
        width: 240,
        alignSelf: 'center',
        backgroundColor: '#F8E71C',
        borderRadius: 0,
        borderWidth: 0,
    },
    txtKakaoLogin: {
        fontSize: 16,
        color: '#3d3d3d',
    },
});
