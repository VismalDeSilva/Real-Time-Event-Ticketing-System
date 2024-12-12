import React from 'react'
import styles from './style.module.scss'
import { Link } from 'react-router-dom'

const index = () => {
  return (
    <div className={styles.container}>
        <Link to="/"><h3 className={styles.logo}>Event Ticketing</h3></Link>
        <div className={styles.navItems}>
            <ul>
                <li><Link to="/">Home</Link></li>
                <li><Link to="/events">Events</Link></li>
                <li><Link to="/profile">Profile</Link></li>
                <li><Link to="/configurator"><button>Configurator</button></Link></li>
            </ul>
        </div>
    </div>
  )
}

export default index