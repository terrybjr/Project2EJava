import React from 'react';
import Card from '@material-ui/core/Card';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Abilities from '../Classes/Abilities';

type State = {
  oAbilities: Abilities;
};

class AbilitiesComponent extends React.Component {
  state: State = { oAbilities: new Abilities() };
  ability_names: string[] = this.state.oAbilities.get_AbilityNames();
  ability_scores: number[] = this.state.oAbilities.get_AbilityScores();

  render() {
    this.ability_scores = this.state.oAbilities.get_AbilityScores();
    return (
      <Card>
        {' '}
        <Grid container>
          <Grid item xs={9}>
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
      </Card>
    );
  }
}

export default AbilitiesComponent;
