import styled, {css} from 'styled-components/native';

export const Heading1 = styled.Text`
  font-size: 26px;
  text-align: center;
  color: ${({theme}) => theme.text};
  font-family: futura;
  font-weight: 700;

  ${({theme: {isDesktop}}) =>
    isDesktop &&
    css`
      font-size: 40px;
    `}
`;

export const Body1 = styled.Text`
  font-size: 20px;
  text-align: center;
  color: ${({theme}) => theme.text};
  font-family: avenir;
  font-weight: 800;

  ${({theme: {isDesktop}}) =>
    isDesktop &&
    css`
      font-size: 28px;
    `}
`;

export const Body3 = styled.Text`
  font-size: 14px;
  color: ${({theme}) => theme.text};
`;
