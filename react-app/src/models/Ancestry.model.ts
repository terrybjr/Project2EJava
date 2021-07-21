export interface Ancestry {
  name: string;
  hitpoints: number;
  size: string;
  speed: number;
  abilityBoostsList: {
    ability: {
      code: string;
    };
    quantity: number;
  }[];
  abilityFlawsList: {
    ability: {
      code: string;
    };
    quantity: number;
  }[];
  languagesList: {
    language: {
      name: 'Common';
    };
  }[];
}
