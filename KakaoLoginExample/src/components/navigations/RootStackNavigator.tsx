import {
  StackNavigationProp,
  createStackNavigator,
} from '@react-navigation/stack';

import Intro from '../pages/Intro';
import {NavigationContainer} from '@react-navigation/native';
import React from 'react';
import Temp from '../pages/Temp';

export type RootStackParamList = {
  default: undefined;
  Intro: undefined;
  Temp: {param: string};
};

export type RootStackNavigationProps<
  T extends keyof RootStackParamList = 'default',
> = StackNavigationProp<RootStackParamList, T>;

const Stack = createStackNavigator<RootStackParamList>();

function RootNavigator(): React.ReactElement {
  return (
    <NavigationContainer>
      {/* @ts-ignore */}
      <Stack.Navigator
        initialRouteName="Intro"
        screenOptions={{
          headerStyle: {
            backgroundColor: 'white',
          },
          headerTitleStyle: {color: 'black'},
          headerTintColor: 'yellow',
        }}
      >
        <Stack.Screen name="Intro" component={Intro} />
        <Stack.Screen name="Temp" component={Temp} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default RootNavigator;
