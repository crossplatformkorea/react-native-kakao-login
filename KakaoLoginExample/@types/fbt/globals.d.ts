/// <reference types="react" />

declare namespace FBT {
  type $Values<T> = T[keyof T];

  // https://github.com/facebookincubator/fbt/blob/e9c591f451dbfc91852e316869ae39ad41848c55/runtime/nonfb/GenderConst.js#L9-L23
  interface GenderConst {
    NOT_A_PERSON: 0;
    FEMALE_SINGULAR: 1;
    MALE_SINGULAR: 2;
    FEMALE_SINGULAR_GUESS: 3;
    MALE_SINGULAR_GUESS: 4;
    MIXED_SINGULAR: 5;
    MIXED_PLURAL: 5;
    NEUTER_SINGULAR: 6;
    UNKNOWN_SINGULAR: 7;
    FEMALE_PLURAL: 8;
    MALE_PLURAL: 9;
    NEUTER_PLURAL: 10;
    UNKNOWN_PLURAL: 11;
  }

  interface IntlVariationsGender {
    GENDER_MALE: 1;
    GENDER_FEMALE: 2;
    GENDER_UNKNOWN: 3;
  }

  // https://github.com/facebookincubator/fbt/blob/e9c591f451dbfc91852e316869ae39ad41848c55/runtime/nonfb/IntlVariations.js#L9-L21
  interface IntlVariations extends IntlVariationsGender {
    BITMASK_NUMBER: 28;
    BITMASK_GENDER: 3;
    NUMBER_ZERO: 16;
    NUMBER_ONE: 4;
    NUMBER_TWO: 8;
    NUMBER_FEW: 20;
    NUMBER_MANY: 12;
    NUMBER_OTHER: 24;
  }

  // https://github.com/facebookincubator/fbt/blob/c2d363a40b622d5aaf80ff1d249b38604fd869f6/transform/babel-plugin-fbt/FbtConstants.js#L22-L27
  type PronounType = 'object' | 'possessive' | 'reflexive' | 'subject';

  type IntlVariationsGenderValues = $Values<IntlVariationsGender>;

  interface IntlViewerContext {
    GENDER: IntlVariationsGenderValues;
    locale: string;
  }

  type IntlVariationsValues = $Values<IntlVariations>;
  type GenderConstValues = $Values<GenderConst>;

  type Translations = {[locale: string]: {[key: string]: string}};

  interface Options {
    author?: string;
    common?: boolean;
    doNotExtract?: boolean;
    preserveWhitespace?: boolean;
    project?: string;
    subject?: IntlVariationsGenderValues;
  }

  interface PluralOptions {
    many?: string;
    showCount?: 'yes' | 'no' | 'ifMany';
    name?: string;
    value?: any;
  }

  interface ParamOptions {
    gender?: IntlVariationsGenderValues;
    number?: number | true;
  }

  interface PronounOptions {
    human?: boolean;
    capitalize?: boolean;
  }

  type FbtResult = string;
}

declare namespace JSX {
  interface IntrinsicElements {
    fbt: {
      desc: string;
      children: React.ReactNode | React.ReactNode[];
    } & FBT.Options;
  }
}
