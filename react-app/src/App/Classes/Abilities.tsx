//import React from 'react';

import { ImportsNotUsedAsValues } from "typescript";

class Abilities {
   str : number;
   dex : number;
   con : number;
   int : number;
   wis : number;
   cha : number;
   scores: number[];
   names: string[];

   constructor() {
      this.str = 10;
      this.dex = 10;
      this.con = 10;
      this.int = 10;
      this.wis = 10;
      this.cha = 10;
      this.scores = [10,10,10,10,10,10];
      this.names = ["Strength","Dexterity","Constitution","Intelligence","Wisdom","Charisma"];
   }

   // getters and setters
   get_str() {
    return this.str;
  }
  set_str(value: number) {
    this.str = value;
  }
  get_dex() {
    return this.dex;
  }
  set_dex(value: number) {
    this.dex = value;
  }
  get_con() {
    return this.con;
  }
  set_con(value: number) {
    this.con = value;
  }
  get_int() {
    return this.int;
  }
  set_int(value: number) {
    this.int = value;
  }
  get_wis() {
    return this.wis;
  }
  set_wis(value: number) {
    this.wis = value;
  }
  get_cha() {
    return this.cha;
  }
  set_cha(value: number) {
    this.cha = value;
  }

  set_scores() {
    this.scores = [this.get_str(), this.get_dex(), this.get_con(), this.get_int(), this.get_wis(), this.get_cha()];
  }
  get_AbilityScores() {
    this.set_scores();
    return this.scores;
  }
  get_AbilityNames() {
    return this.names;
  }

}

export default Abilities;