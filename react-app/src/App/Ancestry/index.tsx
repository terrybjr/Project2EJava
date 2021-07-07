import React from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Grid from '@material-ui/core/Grid';

type Props = {};

class Ancestry extends React.Component {
  chosenCd: string;
  str: number;
  dex: number;
  con: number;
  int: number;
  wis: number;
  cha: number;


  constructor(props: Props) {
    super(props);
    this.chosenCd = 'Dwf';
    this.str = 10;
    this.dex = 10;
    this.con = 10;
    this.int = 10;
    this.wis = 10;
    this.cha = 10;

  }

  set_chosenCd(value: string) {
    this.chosenCd = value;
  }

  get_chosenCd() {
    return this.chosenCd;
  }

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

  get_List() {
    return (
      <div>
        <Grid container spacing={5} xs={6} alignItems="center">
          <Grid item xs={6}>
      <List>
      {['Dwarf', 'Elf', 'Gnome', 'Halfling', 'Human', 'Orc'].map((text, index) => (
        <ListItem button key={text}>
          <ListItemText primary={text} />
        </ListItem>
        // eslint-disable-next-line @typescript-eslint/comma-dangle
      ))}
    </List>
    </Grid>
    <Grid container xs={6}>
    {this.get_Abilities()}
    </Grid>
    </Grid>
    </div>
    );
  }

  get_Abilities() {
    return (
      <Grid container spacing={5} xs={9}>
        <Grid item xs={6}>
          Strength: 
          </Grid>
        <Grid item xs={3}>
          {this.get_str()}
        </Grid>          
      <Grid item xs={6}>
        <div>Dexterity: </div>
        </Grid>
        <Grid item xs={3}>
        <div>{this.get_dex()}</div>
        </Grid>        
      <Grid item xs={6}>
        <div>Constitution: </div>
        </Grid>
        <Grid item xs={3}>
        <div>{this.get_con()}</div>
        </Grid>
      <Grid item xs={6}>
        <div>Intelligence: </div>
        </Grid>
        <Grid item xs={3}>
        <div>{this.get_int()}</div>
        </Grid>
      <Grid item xs={6}>
        <div className="column">Wisdom: </div>
        </Grid>
        <Grid item xs={3}>
        <div className="column">{this.get_wis()}</div>
        </Grid>
      <Grid item xs={6}>
        <div className="column">Charisma: </div>
        </Grid>
        <Grid item xs={3}>
        <div className="column">{this.get_cha()}</div>
        </Grid>             
      </Grid>
    );
  }

  onSelect() {
    this.set_chosenCd('Dwarf');
  }

  render() {
    return (
      // <List>
      //   {['Dwarf', 'Elf', 'Gnome', 'Halfling', 'Human', 'Orc'].map((text, index) => (
      //     <ListItem button key={text}>
      //       <ListItemText primary={text} />
      //     </ListItem>
      //     // eslint-disable-next-line @typescript-eslint/comma-dangle
      //   ))}
      // </List>
      this.get_List()
    );
  }
}

export default Ancestry;
