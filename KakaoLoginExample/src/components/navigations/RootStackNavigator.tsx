import {
  StackNavigationProp,
  createStackNavigator,
} from '@react-navigation/stack';

import Intro from '../pages/Intro';
import {NavigationContainer} from '@react-navigation/native';
import React from 'react';
import Temp from '../pages/Temp';
import {useTheme} from 'dooboo-ui';

export type RootStackParamList = {
  default: undefined;
  Intro: undefined;
  Temp: {param: string};
};

export type RootStackNavigationProps<
  T extends keyof RootStackParamList = 'default'
> = StackNavigationProp<RootStackParamList, T>;

const Stack = createStackNavigator<RootStackParamList>();

function RootNavigator(): React.ReactElement {
  const {theme} = useTheme();

  return (
    <NavigationContainer
      theme={{
        colors: {
          background: theme.background,
          border: theme.border,
          card: theme.paper,
          primary: theme.primary,
          notification: theme.primary,
          text: theme.text,
        },
        dark: true,
      }}>
      <Stack.Navigator
        initialRouteName="Intro"
        screenOptions={{
          headerStyle: {
            backgroundColor: theme.background,
          },
          headerTitleStyle: {color: theme.text},
          headerTintColor: theme.primary,
        }}>
        <Stack.Screen name="Intro" component={Intro} />
        <Stack.Screen name="Temp" component={Temp} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default RootNavigator;
