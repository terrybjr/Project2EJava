import React from 'react';
import Abilities from '../Classes/Abilities';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Grid from '@material-ui/core/Grid';
import { fetchAncestryData } from '../../api/static-data';
import { Ancestry as AncestryInterface } from '../../models/Ancestry.model';

type State = {
  ancestryTypes: AncestryInterface[];
  chosenCd: string;
  oAbilities: Abilities;
};

class Ancestry extends React.Component {
  state: State = {
    ancestryTypes: [],
    chosenCd: 'Dwarf',
    oAbilities: new Abilities(),
  };
  ability_names: string[] = this.state.oAbilities.get_AbilityNames();
  ability_scores: number[] = this.state.oAbilities.get_AbilityScores();

  componentDidMount() {
    this.fetchData();
  }

  async fetchData() {
    await fetchAncestryData()
      .then((res) => this.setState({ ...this.state, ancestryTypes: res.data }))
      .catch((e) => console.error(e));
  }

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
    this.ability_scores = this.state.oAbilities.get_AbilityScores();

    return (
      <Grid container spacing={5}>
        <Grid item xs={6}>
          <List>
            {this.ability_names.map((text) => (
              <ListItem key={text}>
                <ListItemText primary={text} />
              </ListItem>
            ))}
          </List>
        </Grid>
        <Grid item xs={3}>
          <List>
            {this.ability_scores.map((text) => (
              <ListItem key={text}>
                <ListItemText primary={text} />
              </ListItem>
            ))}
          </List>
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
            {this.state.ancestryTypes.map((ancestry) => (
              <ListItem
                selected={this.is_selected_item(ancestry.name)}
                onClick={() => this.onSelect(ancestry.name)}
                button
                key={ancestry.name}
              >
                <ListItemText primary={ancestry.name} />
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
