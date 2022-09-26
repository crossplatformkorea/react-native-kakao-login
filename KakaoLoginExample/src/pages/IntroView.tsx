import { ScrollView, StyleSheet, Text, View } from 'react-native';

import React from 'react';

type Props = {
  result: string;
};

function IntroView({ result }: Props): React.ReactElement {
  return (
    <View style={styles.container}>
      <ScrollView>
        <Text>{result}</Text>
        <View style={{ height: 100 }} />
      </ScrollView>
    </View>
  );
}

export default IntroView;

const styles = StyleSheet.create({
  container: {
    flexDirection: "column",
    width: "100%",
    padding: 24,
  }
});
