import React from 'react';
import Card from '@material-ui/core/Card';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Abilities from '../Classes/Abilities';
import { connect } from 'react-redux';
import { Ancestry } from '../../models/Ancestry.model';

type State = {
  oAbilities: Abilities;
  ability_scores: number[];
  ability_names: string[];
};

type Props = {
  ancestry: Ancestry;
};

class AbilitiesComponent extends React.Component<Props, State> {
  state: State = {
    oAbilities: new Abilities(),
    ability_names: [],
    ability_scores: [],
  };

  componentDidMount() {
    this.setState({
      ...this.state,
      ability_names: this.state.oAbilities.get_AbilityNames(),
      ability_scores: this.state.oAbilities.get_AbilityScores(),
    });
  }

  componentDidUpdate(prevProps: Props) {
    if (prevProps.ancestry === this.props.ancestry) return;
    const boosts = this.props.ancestry.abilityBoostsList;
    const flaws = this.props.ancestry.abilityFlawsList;
    const abilities = this.state.ability_names.map((name, i) => ({
      code: name[0],
      score: this.state.oAbilities.get_AbilityScores()[i],
    }));
    const newScores = abilities.map((ability) => {
      const { code } = ability;
      let { score } = ability;
      const boost = boosts.find((b) => b.ability.code === code);
      if (boost) {
        score += 2 * boost.quantity;
      }
      const flaw = flaws.find((f) => f.ability.code === code);
      if (flaw) {
        score -= 2 * flaw.quantity;
      }
      return score;
    });
    this.setState({ ...this.state, ability_scores: newScores });
  }

  render() {
    return (
      <Card>
        {' '}
        <Grid container>
          <Grid item xs={9}>
            <List>
              {this.state.ability_names.map((text) => (
                <ListItem key={text}>
                  <ListItemText primary={text} />
                </ListItem>
              ))}
            </List>
          </Grid>
          <Grid item xs={3}>
            <List>
              {this.state.ability_scores.map((score, index) => (
                <ListItem key={`score${index}`}>
                  <ListItemText primary={score} />
                </ListItem>
              ))}
            </List>
          </Grid>
        </Grid>
      </Card>
    );
  }
}

const mapStateToProps = (state: { ancestry: Ancestry }) => {
  const { ancestry } = state;
  return { ancestry };
};

export default connect(mapStateToProps)(AbilitiesComponent);
