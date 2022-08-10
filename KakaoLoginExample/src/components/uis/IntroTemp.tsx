import {ScrollView, View} from 'react-native';

import React from 'react';
import styled from 'styled-components/native';

const ContentWrapper = styled.View`
  flex-direction: column;
  height: 100%;
  width: 100%;
  padding: 24px;
  justify-content: flex-start;
  align-items: center;
`;

const StyledText = styled.Text`
  font-size: 18px;
  line-height: 27px;
`;

type Props = {
  result: string;
};

function IntroView({result}: Props): React.ReactElement {
  return (
    <ContentWrapper>
      {/* @ts-ignore */}
      <ScrollView>
        <StyledText>{result}</StyledText>
        {/* @ts-ignore */}
        <View style={{height: 400}} />
      </ScrollView>
    </ContentWrapper>
  );
}

export default IntroView;
