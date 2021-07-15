import React from 'react';
import Abilities from '../Classes/Abilities';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Grid from '@material-ui/core/Grid';

type Props = {};

class Ancestry extends React.Component {
  chosenCd: string;
  oAbilities : Abilities;

  constructor(props: Props) {
    super(props);
    this.chosenCd = 'Dwf';
    this.oAbilities = new Abilities();
  }

  set_chosenCd(value: string) {
    this.chosenCd = value;
  }

  get_chosenCd() {
    return this.chosenCd;
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
          {this.oAbilities.get_str()}
        </Grid>          
      <Grid item xs={6}>
        <div>Dexterity: </div>
        </Grid>
        <Grid item xs={3}>
        <div>{this.oAbilities.get_dex()}</div>
        </Grid>        
      <Grid item xs={6}>
        <div>Constitution: </div>
        </Grid>
        <Grid item xs={3}>
        <div>{this.oAbilities.get_con()}</div>
        </Grid>
      <Grid item xs={6}>
        <div>Intelligence: </div>
        </Grid>
        <Grid item xs={3}>
        <div>{this.oAbilities.get_int()}</div>
        </Grid>
      <Grid item xs={6}>
        <div className="column">Wisdom: </div>
        </Grid>
        <Grid item xs={3}>
        <div className="column">{this.oAbilities.get_wis()}</div>
        </Grid>
      <Grid item xs={6}>
        <div className="column">Charisma: </div>
        </Grid>
        <Grid item xs={3}>
        <div className="column">{this.oAbilities.get_cha()}</div>
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
