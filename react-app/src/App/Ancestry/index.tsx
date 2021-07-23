import React from 'react';
import { Card } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { fetchAncestryData } from '../../api/static-data';
import { Ancestry as AncestryInterface } from '../../models/Ancestry.model';
import './index.css';

type State = {
  ancestryTypes: AncestryInterface[];
  chosenCd: string;
  mouseOn: number;
};

class Ancestry extends React.Component {
  state: State = {
    ancestryTypes: [],
    chosenCd: '',
    mouseOn: 100,
  };

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

  renderDescription() {
    if (this.state.mouseOn !== 100) {
      return (
        <div className="description">
          <h1>{this.state.ancestryTypes[this.state.mouseOn].name}</h1>
          <p />
          Hit Points: {this.state.ancestryTypes[this.state.mouseOn].hitpoints}
          <br />
          Speed: {this.state.ancestryTypes[this.state.mouseOn].speed} feet per round
          <br />
          Size: {this.state.ancestryTypes[this.state.mouseOn].size}
          <br />
        </div>
      );
    }
    return <></>;
  }

  onSelect(text: string) {
    this.set_chosenCd(text);
  }

  getShortDescription(ancestry: AncestryInterface): string {
    const size = `Size: ${ancestry.size[0]}`;
    const hp = `HP: ${ancestry.hitpoints}`;
    const speed = `Speed: ${ancestry.speed}`;
    const boosts = ancestry.abilityBoostsList.reduce(
      (acc, boost) => `${acc} +${boost.quantity * 2}${boost.ability.code}`,
      ''
    );
    const flaws = ancestry.abilityFlawsList.reduce(
      (acc, flaw) => `${acc} -${flaw.quantity * 2}${flaw.ability.code}`,
      ''
    );
    return `${size} ${hp} ${speed} Modifiers: ${boosts} ${flaws}`;
  }

  render() {
    return (
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Card>
            <List>
              <ListItem>
                <ListItemText primary="Select an Ancestry" />
              </ListItem>
              {this.state.ancestryTypes.map((ancestry, index) => (
                <ListItem
                  selected={this.is_selected_item(ancestry.name)}
                  onClick={() => this.onSelect(ancestry.name)}
                  onMouseEnter={() => this.setState({ mouseOn: index })}
                  onMouseLeave={() => this.setState({ mouseOn: 100 })}
                  button
                  key={ancestry.name}
                >
                  <ListItemText
                    primary={ancestry.name}
                    secondary={this.getShortDescription(ancestry)}
                  />
                </ListItem>
              ))}
              <ListItem>
                <ListItemText secondary="Hover over an Ancestry for more description" />
              </ListItem>
            </List>
          </Card>
        </Grid>
        <Grid item xs={12}>
          <Card>{this.renderDescription()}</Card>
        </Grid>
      </Grid>
    );
  }
}

export default Ancestry;
