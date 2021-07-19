import React from 'react';
import Abilities from '../Classes/Abilities';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Grid from '@material-ui/core/Grid';

type State = {
  chosenCd: string;
  oAbilities: Abilities;
};

class Ancestry extends React.Component {
  state: State = {
    chosenCd: 'Dwarf',
    oAbilities: new Abilities(),
  };
  ancestryTypes: string[] = ['Dwarf', 'Elf', 'Gnome', 'Halfling', 'Human', 'Orc'];

  is_selected_item(ancestryType: string): boolean {
    return ancestryType === this.state.chosenCd;
  }

  set_chosenCd(value: string) {
    this.setState({
      ...this.state,
      chosenCd: value,
    });
  }

  get_chosenCd() {
    return this.state.chosenCd;
  }

  renderRow(text: string) {
    return (
      <ListItem button key={text}>
        <ListItemText primary={text} />
      </ListItem>
    );
  }

  get_Abilities() {
    return (
      <Grid container spacing={5}>
        <Grid item xs={6}>
          Strength:
        </Grid>
        <Grid item xs={3}>
          {this.state.oAbilities.get_str()}
        </Grid>
        <Grid item xs={6}>
          <div>Dexterity: </div>
        </Grid>
        <Grid item xs={3}>
          <div>{this.state.oAbilities.get_dex()}</div>
        </Grid>
        <Grid item xs={6}>
          <div>Constitution: </div>
        </Grid>
        <Grid item xs={3}>
          <div>{this.state.oAbilities.get_con()}</div>
        </Grid>
        <Grid item xs={6}>
          <div>Intelligence: </div>
        </Grid>
        <Grid item xs={3}>
          <div>{this.state.oAbilities.get_int()}</div>
        </Grid>
        <Grid item xs={6}>
          <div className="column">Wisdom: </div>
        </Grid>
        <Grid item xs={3}>
          <div className="column">{this.state.oAbilities.get_wis()}</div>
        </Grid>
        <Grid item xs={6}>
          <div className="column">Charisma: </div>
        </Grid>
        <Grid item xs={3}>
          <div className="column">{this.state.oAbilities.get_cha()}</div>
        </Grid>
      </Grid>
    );
  }

  onSelect(text: string) {
    this.set_chosenCd(text);
  }

  render() {
    return (
      <Grid container spacing={5} alignItems="center">
        <Grid item xs={6}>
          <List>
            {this.ancestryTypes.map((text) => (
              <ListItem
                selected={this.is_selected_item(text)}
                onClick={() => this.onSelect(text)}
                button
                key={text}
              >
                <ListItemText primary={text} />
              </ListItem>
            ))}
          </List>
        </Grid>
        <Grid item xs={6}>
          {this.get_Abilities()}
        </Grid>
      </Grid>
    );
  }
}

export default Ancestry;
