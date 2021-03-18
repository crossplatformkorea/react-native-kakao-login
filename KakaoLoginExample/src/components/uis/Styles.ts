import styled from 'styled-components/native';

export const StyledButton = styled.View`
  background-color: ${({theme}) => theme.primary};
  align-self: center;
  border-radius: 4px;
  border-width: 2px;
  width: 320px;
  height: 52px;
  border-color: ${({theme}) => theme.primary};

  align-items: center;
  justify-content: center;
`;

export const TransparentButton = styled.View`
  display: flex;
  width: 100%;
  height: 100%;

  border-radius: 3;
  border-style: solid;
  border-width: 1;
  border-color: #e3e3e3;
  margin-bottom: 8;

  justify-content: center;
  align-items: center;
  background: rgba(235, 87, 87, 0.03);
  color: #333;
  opacity: 1;

  &:hover {
    opacity: 0.5;
  }
  &:active {
    opacity: 0.5;
  }
`;

export const WhiteButton = styled.View`
  display: flex;
  width: 100%;
  height: 100%;
  border-radius: 3;
  border-style: solid;
  border-width: 1;
  margin-bottom: 8;

  justify-content: center;
  align-items: center;
  opacity: 1;

  &:hover {
    opacity: 0.5;
  }
  &:active {
    opacity: 0.5;
  }
`;
